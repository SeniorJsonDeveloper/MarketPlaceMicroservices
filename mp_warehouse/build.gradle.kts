plugins {
    java
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "dn"
version = "0.0.1-SNAPSHOT"


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}


configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2024.0.0")
    }
}


dependencies {
    implementation("io.swagger.core.v3:swagger-annotations:2.2.25")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("redis.clients:jedis:5.2.0")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    annotationProcessor ("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    annotationProcessor ("org.mapstruct:mapstruct-processor:1.5.5.Final")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework.kafka:spring-kafka")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")

}



    tasks.withType<Test> {
        useJUnitPlatform()
    }

