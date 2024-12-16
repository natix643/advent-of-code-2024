plugins {
    kotlin("jvm") version "1.9.25"
    id("org.springframework.boot") version "3.4.0"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

springBoot {
    mainClass.set("aoc2024.day06.Day06bKt")
}
