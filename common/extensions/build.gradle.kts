dependencies {
    implementation(project(":common:exceptions"))
    
    implementation(platform("io.vertx:vertx-stack-depchain:${project.property("vertx.version")}"))
    implementation("io.vertx:vertx-web-client")
    implementation("io.vertx:vertx-web")
    implementation("io.vertx:vertx-pg-client")
    implementation("io.vertx:vertx-rx-java3")
    implementation("io.vertx:vertx-lang-kotlin")
    implementation("io.vertx:vertx-core")
    implementation("io.github.cdimascio:dotenv-kotlin:${project.property("dotenv")}")
}
