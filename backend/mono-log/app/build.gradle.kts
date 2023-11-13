import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED

val mainVerticle = "org.xepelin_bank.mono_log.app.configure.MainMonoLogVerticle"
val launcherClassName = "org.xepelin_bank.mono_log.app.MonoLogAPI"
val watchForChange = "src/**/*"
val doOnChange = "${projectDir}/gradlew classes"

group = "org.xepelin_bank"
version = "1.0.0-SNAPSHOT"

plugins {
    application
    id("com.github.johnrengelman.shadow") version "8.0.0"
}

application {
    mainClass.set(launcherClassName)
}

dependencies {
    implementation(project(":infrastructure:vertx"))
    
    implementation(project(":common:exceptions"))
    implementation(project(":common:extensions"))
    
    implementation(project(":backend:mono-log:adapters"))
    
    implementation(project(":infrastructure:flyway"))
    
    implementation(platform("io.vertx:vertx-stack-depchain:${project.property("vertx.version")}"))
    implementation("io.vertx:vertx-web")
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "17"

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveClassifier.set("fat")
    manifest {
        attributes(mapOf("Main-Verticle" to mainVerticle))
    }
    mergeServiceFiles()
}

tasks.withType<JavaExec> {
    args = listOf(
        "run",
        mainVerticle,
        "--redeploy=$watchForChange",
        "--launcher-class=$launcherClassName",
        "--on-redeploy=$doOnChange"
    )
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events = setOf(
            PASSED, SKIPPED, FAILED
        )
    }
}
