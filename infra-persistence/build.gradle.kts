import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins { id("io.spring.dependency-management") }

dependencyManagement {
    imports { mavenBom(SpringBootPlugin.BOM_COORDINATES) }
}

dependencies {
    implementation(project(":kernel"))
    implementation(project(":ledger"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
