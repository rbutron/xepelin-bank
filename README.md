# Ejercicio - Xepelin


- [Ejercicio](#ejercicio)
    - [Especificaciones](#especificaciones)
    - [Tecnologias](#tecnologias)
    - [Arquitectura](#arquitectura)
- [Setup](#setup)
    - [Instrucciones](#instrucciones)
- [Cobertura](#cobertura)

## Ejercicio

### Especificaciones

Se requiere implementar una automatizacion que permita la creacion de cuentas bancarias.

### Tecnologias

- [Java11](https://jdk.java.net/17/)
- [Kotlin](https://kotlinlang.org/)
- [Vert.x](https://vertx.io/)
- [RX Java3](http://reactivex.io/)
- [JUnit5](https://junit.org/junit5/docs/current/user-guide/#overview-getting-started)

La solucion esta apalancada en el marco de estrategias GRASP, basado en los conceptos que nos
expone Chris Richartdson con su libro Microservices Pattern tomando de esto los conceptos de
Event Sourcing y Event Driven, como tambien ciertas articulaciones con SAGA para que los eventos de
cada cada transaccion local, publiquen eventos de dominio que desencaden transacciones 
locales en otros servicios (Choreography Strategy).

Tambien puedo resaltar que se adecua los estandares o patrones DDD para la separacion de responsabilidades y sus patrones
de diseño, como muestro en las imagenes a continuacion:

Tenemos un mono repo de MS con tenenidos con la herramienta Gradle

![img.png](doc/img1.png)

haremos un pequeño tour por de como esta solucion responde a las necesidades bancarias que nos resalta la prueba, para ello 
creamos 3 Micro Servicios usando la estrategia database por servicio y API Composition, vemos que los los servicios estan 
aislados por infrastructure, common, adapters, app y domain

![img.png](doc/img2.png)

![img3.png](doc/img3.png)

Asi como vemos en nuestras carpetas de trabajo reducimos estos componentes con esto

![img.png](doc/img.png)
## Setup

### Instrucciones

Para dar marcha a este proyecto debemos tener Java17 y Gradle 8.4.0 (por suerte tenemos Docker :D) para poder asi configurar las dependencias de este.

1. Clonamos el proyecto
2. Ejecutamos ```docker compose  -f "docker-compose.yml" up -d```
3. Abrimos nuestra consola y ejecutamos o copiamos estos curls para que se nos faciliten en el postman

Revisamos nuestros MS que esten arriba despues de que el Docker finalice
```
curl --location 'http://localhost:9040/account/health-check'
```

```
curl --location 'http://localhost:9030/transaction/health-check'
```

```
curl --location 'http://localhost:9020/mono-log/health-check'
```

Por ultimo hacemos el proceso de creacion de cuenta 

```curl --location 'http://localhost:9040/account/api/v1/account' \
--header 'Content-Type: application/json' \
--data '{
"account-name": "Tu Nombre",
"account-number": "123456",
"amount": "80"
}
'
```
Si queremos revisar nuestro balance mira el enlace de abajo :v

```
curl --location 'http://localhost:9040/account/api/v1/account/bd6df63f-1636-4470-9bd6-5b24631f71ac/balance'
```
Aqui unas pruebas del flujo de como el MS interactua con Account -> Mono-Log -> Account, para dejar track de todo los procesos que hacen lo comando y eventos

![img.png](doc/img0.png)

![img.png](doc/img4.png)

![img.png](doc/img5.png)

Espero tener la oportunidad para explicarlo con mas detalle, excelente dia