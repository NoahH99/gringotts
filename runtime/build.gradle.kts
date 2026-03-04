plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":kernel"))
    implementation(project(":ledger"))
    implementation(project(":gateway"))
    implementation(project(":infra-persistence"))
    implementation(project(":infra-observability"))
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
