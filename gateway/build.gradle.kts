import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins { id("io.spring.dependency-management") }

dependencyManagement {
    imports { mavenBom(SpringBootPlugin.BOM_COORDINATES) }
}

dependencies {
    implementation(project(":kernel"))
    implementation("net.dv8tion:JDA:6.3.1") {
        exclude(module = "opus-java")
        exclude(module = "tink")
    }
    implementation("org.springframework:spring-context")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
}
