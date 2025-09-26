plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    api("com.fasterxml.jackson.core:jackson-annotations:2.17.0")

    api("jakarta.validation:jakarta.validation-api:3.0.2")
}
