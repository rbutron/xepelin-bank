import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
    }
}

plugins {
    kotlin("jvm") version "2.0.0"
}

allprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {

        implementation(platform("io.vertx:vertx-stack-depchain:${project.property("vertx.version")}"))
        implementation("io.vertx:vertx-rx-java3")

        implementation("org.slf4j:jcl-over-slf4j:${project.property("slf4j.version")}")
        implementation("org.slf4j:slf4j-api:${project.property("slf4j.version")}")
        implementation("org.slf4j:slf4j-simple:${project.property("slf4j.version")}")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${project.property("jackson-module.version")}")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${project.property("jackson-module.version")}")
        implementation("com.fasterxml.jackson.module:jackson-module-parameter-names:${project.property("jackson-module.version")}")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:${project.property("jackson-module.version")}")

        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${project.property("kotlin-stdlib.version")}")

        implementation("com.google.inject:guice:${project.property("google-inject.version")}")
    }

    repositories {
        mavenCentral()
    }
}
