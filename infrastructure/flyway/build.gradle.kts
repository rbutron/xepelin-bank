dependencies {
    implementation(project(":common:extensions"))
    
    implementation("com.google.inject:guice:${project.property("google-inject.version")}")
    
    implementation("org.flywaydb:flyway-core:${project.property("flyway.version")}")
}
