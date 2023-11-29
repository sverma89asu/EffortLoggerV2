# EffortLogger V2

## Description
![All Tests](https://github.com/sverma89asu/EffortLoggerV2/actions/workflows/maven.yml/badge.svg)

Effort Logger Version 2, for CSE360 at ASU. Made on the vaguest of premises and least sensible of information.
Capable of doing asynchronous planning poker, or of editing many values. 

## Installation
1. `git clone` This repository.
2. If installed Java Version <17, install Java 17+ (see [adoptium.net](https://adoptium.net)).
3. [Optional] Install Maven (see [maven.apache.org](https://maven.apache.org/users/index.html))

## Usage
1. Navigate to project root (the directory with `pom.xml`).

2.  
   - If Maven is installed on system: `mvn clean javafx:run`
   - If Maven **is not** installed on system: `./mvnw.cmd clean javafx:run`

## Tests
1. Navigate to project root (the directory with `pom.xml`).

2.
   - If Maven is installed on system: `mvn test`
   - If Maven **is not** installed on system: `./mvnw.cmd test`

### Test Coverage
Automated End-to-end package tests cover the `tu14.api` package.
In `tu14.api`, Tests cover 100% of classes, ~70% lines and methods.  

## Requirements
- Java SE 17+ 
- Maven or Maven Wrapper (included)
- JavaFX Runtime 
- Maven Dependencies (See Below):

| Name                  | Version  | Maven Artifact                                         |
|-----------------------|----------|--------------------------------------------------------|
| JavaFX Web            | 19.0.2.1 | org.openjfx:javafx-web                                 |
| JavaFX FXML           | 19.0.2.1 | org.openjfx:javafx-fxml                                |
| JUnit Jupiter API     | 5.9.2    | org.junit.jupiter:junit-jupiter-api                    |
| Junit Jupiter Engine  | 5.9.2    | org.junit.jupiter:junit-jupiter-engine                 |
| Jackson JSON Databind | 2.15.1   | com.fasterxml.jackson.core:jackson-databind            |
| Jackson Time Plugin   | 2.15.1   | com.fasterxml.jackson.datatype:jackson-datatype-jsr310 |
| Apache Commons Text   | 1.10.0   | org.apache.commons:commons-text                        | 
| Maven Surefire Plugin | 3.2.2    | org.apache.maven.plugins:maven-surefire-plugin         |
| Maven JavaFX Plugin   | 0.0.8    | org.openjfx:javafx-maven-plugin                        |
| Maven Shade Plugin    | 3.0.0    | org.apache.maven.plugins:maven-shade-plugin            |

## License
See LICENSE file


