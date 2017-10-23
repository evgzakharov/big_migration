# big_migration
Example of migration from Java project on Spring Boot 1.5 to Kotlin and Spring-webflux. And From Groovy Dsl to Kotlin Dsl in Gradle

## Step 1 
Simple project on Java and Spring Boot 1.5 with two controllers, service and in-memory repository. Project build by Groovy Dsl in Gradle

## Step 2
Migrate to Spring Boot 2 and Junit 5. Main difference appears in tests and dependencies in build.gradle. 

## Step 3
Convert code to Kotlin, mostly by Intellij Idea and after clean code to Kotlin style. For mocking add [mockito-kotlin library](https://github.com/nhaarman/mockito-kotlin)

## Step 4
Convert code to webflux. For base project take [this](https://github.com/sdeleuze/spring-kotlin-functional).
Have many problems with enable annotation processing and loading a configuration. [Description](https://stackoverflow.com/questions/46834767/how-to-load-config-in-spring-webflux-without-spring-boot) of founded solution.  

## Step 5
Convert Gradle Dsl to Kotlin Dsl. For now worked only if moving Kotlin plugin to new Gradle plugin syntax. 
