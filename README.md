[![travis image](https://travis-ci.org/evgzakharov/big_migration.svg?branch=master)]

# Example of migration step by step to new technologies
Example of migration from Java project on Spring Boot 1.5 to Kotlin, Spring Boot 2 and after to Spring-webflux. 
And From Groovy Dsl to Kotlin Dsl in Gradle

## Step 1 
Simple project on Java and Spring Boot 1.5 with two controllers, service and in-memory repository. Project build by Groovy Dsl in Gradle

## Step 2
Migrate to Spring Boot 2 and Junit 5. Main difference appears in tests and dependencies in build.gradle. 

## Step 3
Convert code to Kotlin, mostly by Intellij Idea and after clean code to Kotlin style. For mocking add [mockito-kotlin library](https://github.com/nhaarman/mockito-kotlin)

## Step 4
Convert code to webflux. For base project take [this](https://github.com/sdeleuze/spring-kotlin-functional).
Add several BeanPostProcessors to process spring annotations and add manual config loading. 
For the reason that yml processing classes I have found only in spring-boot-starter, it's needed to include this dependency to build.gradle

## Step 5
Convert Gradle Dsl to Kotlin Dsl. For now worked only if moving Kotlin plugin to new Gradle plugin syntax. 
