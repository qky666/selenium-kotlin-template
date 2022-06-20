import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "es.mtp"
version = "0.1.0"

plugins {
    val kotlinVersion = "1.7.0"

    kotlin("jvm") version kotlinVersion
    `java-library`
    id("com.github.ben-manes.versions") version "0.42.0"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    val kotlinVersion = "1.7.0"

    // https://mvnrepository.com/artifact/com.codeborne/selenide
    testImplementation("com.codeborne:selenide:6.6.3")

    // https://jitpack.io/#qky666/selenide-pom
    testImplementation("com.github.qky666:selenide-pom:0.8.6")

    // https://mvnrepository.com/artifact/io.github.microutils/kotlin-logging
    testImplementation("io.github.microutils:kotlin-logging:2.1.23")

    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-slf4j-impl
    testImplementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.2")

    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api-kotlin
    testImplementation("org.apache.logging.log4j:log4j-api-kotlin:1.1.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
    testImplementation(kotlin("test"))
}

tasks.test {
    useTestNG { suiteXmlFiles = listOf(File("src/test/resources/testng.xml")) }
}

tasks.withType<KotlinCompile> {
    // kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.jvmTarget = "17"
    // kotlinOptions.freeCompilerArgs += "-Xjvm-default=all"
    // kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}
