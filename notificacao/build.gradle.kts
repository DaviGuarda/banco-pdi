plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "davidev"
version = "0.0.1-SNAPSHOT"
description = "API NOTIFICACAO"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
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

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("org.springframework.kafka:spring-kafka")

    implementation(project(":common-dtos"))

    compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
