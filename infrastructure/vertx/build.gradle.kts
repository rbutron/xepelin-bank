dependencies {
    implementation(project(":infrastructure:flyway"))

    implementation(project(":common:exceptions"))
    implementation(project(":common:extensions"))
    
    implementation("org.postgresql:postgresql:${project.property("postgresql.version")}")
    
    implementation("io.vertx:vertx-web")
    implementation("io.vertx:vertx-pg-client")
    implementation("io.vertx:vertx-lang-kotlin")
    implementation("io.vertx:vertx-core")
    implementation("io.vertx:vertx-kafka-client")
    
    implementation("com.google.inject:guice:${project.property("google-inject.version")}")
    
    implementation("com.ongres.scram:client:${project.property("scram.version")}")
    implementation("com.ongres.scram:common:${project.property("scram.version")}")
}
