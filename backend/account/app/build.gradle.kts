import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

val mainVerticle = "org.xepelin_bank.account.app.configure.MainAccountVerticle"
val launcherClassName = "org.xepelin_bank.account.app.AccountAPI"
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
    
    implementation(project(":backend:account:adapters"))
    
    implementation(project(":infrastructure:flyway"))
    
    implementation(platform("io.vertx:vertx-stack-depchain:${project.property("vertx.version")}"))
    implementation("io.vertx:vertx-web")
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
    }
}

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
