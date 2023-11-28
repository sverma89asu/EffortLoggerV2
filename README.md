## EffortLogger V2

### Description

### Installation
- `git clone` This repository.
- If installed Java Version <17, install Java 17+ (see [adoptium.net](https://adoptium.net)).
- [Optional] Install Maven (see [maven.apache.org](https://maven.apache.org/users/index.html))

Am way too lazy to go through the hell of modularizing this, so there's no instructions for a 
packaged install, lol.

### Usage
- If Maven is installed on system: `mvn clean javafx:run`
- If Maven **is not** installed on system: `./mvnw.cmd clean javafx:run`

### Tests
- If Maven is installed on system: `mvn test`
- If Maven **is not** installed on system: `./mvnw.cmd test`

#### Test Coverage
Automated End-to-end package tests cover the `tu14.api` package.
In `tu14.api`, Tests cover 100% of classes, ~70% lines and methods.  

### Requirements
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

### License
You're kidding right? This is school work, lol. I guess, if you want it, the BSD 3-clause license:

Copyright 2023 Shikha Verma

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


