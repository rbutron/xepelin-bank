dependencies {
    implementation(project(":infrastructure:vertx"))

    implementation(project(":common:exceptions"))
    implementation(project(":common:extensions"))
    
    implementation(project(":infrastructure:flyway"))
    
    implementation(project(":backend:transaction:domain"))
}
