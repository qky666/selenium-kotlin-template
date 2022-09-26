import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "es.mtp"
version = "0.12.1"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

plugins {
    kotlin("jvm") version "1.7.10"
    id("com.github.ben-manes.versions") version "0.42.0"
    id("io.qameta.allure") version "2.11.0"
}

dependencies {
    testImplementation("com.codeborne:selenide:6.8.0")
    testImplementation("com.github.qky666:selenide-pom:0.12.1")
    testImplementation("org.apache.logging.log4j:log4j-slf4j-impl:2.19.0")
    testImplementation("org.apache.logging.log4j:log4j-api-kotlin:1.2.0")
    testImplementation(kotlin("test"))
}

allure {
    version.set("2.18.1")
}

tasks.test {
    useTestNG { suiteXmlFiles = listOf(File("src/test/resources/testng.xml")) }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

task<Exec>("allureCombine") {
    commandLine(
        "poetry",
        "run",
        "ac",
        "$buildDir/reports/allure-report/allureReport",
        "--dest",
        "$buildDir/reports/allure-combine",
        "--auto-create-folders",
        "--remove-temp-files"
    )
    setDependsOn(listOf("allureReport"))
}
