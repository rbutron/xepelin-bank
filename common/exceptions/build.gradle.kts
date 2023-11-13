dependencies {
    implementation(platform("io.vertx:vertx-stack-depchain:${project.property("vertx.version")}"))
    implementation("io.vertx:vertx-web")
    implementation("io.vertx:vertx-core")
    implementation("io.vertx:vertx-rx-java3")
}
