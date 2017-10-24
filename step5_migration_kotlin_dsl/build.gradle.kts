import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "evgzakharov"
version = "1.0-SNAPSHOT"

buildscript {
    val springBootVersion by extra { "2.0.0.M5" }
    extra["junitVersion"] = "5.0.1"
    extra["kotlinVersion"] = "1.1.51"

    repositories {
        jcenter()
        maven { setUrl("http://repo.spring.io/milestone") }
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.1.51")
    }
}

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.1.51"
}

apply {
    plugin("org.springframework.boot")
    plugin("org.junit.platform.gradle.plugin")
    plugin("io.spring.dependency-management")
}

repositories {
    jcenter()
    maven { setUrl("http://repo.spring.io/milestone") }
}

val springBootVersion: String by project.extra
val junitVersion: String by project.extra
val kotlinVersion: String by project.extra

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:$kotlinVersion")
    compile("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    compile("org.springframework.boot:spring-boot-starter:$springBootVersion")

    compile("org.springframework:spring-webflux")

    compile("io.undertow:undertow-core")

    compile("com.samskivert:jmustache")

    compile("com.fasterxml.jackson.module:jackson-module-kotlin")

    testCompile("com.nhaarman:mockito-kotlin-kt1.1:1.5.0")

    testCompile("com.squareup.okhttp3:okhttp:3.9.0")

    testCompile("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testCompile("org.junit.platform:junit-platform-runner:1.0.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
