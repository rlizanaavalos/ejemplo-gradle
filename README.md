# Getting Started

## Linux

### Compile, test, jar
* ./gradlew build

### Run Jar
* Local:      ./gradlew bootRun 
* Background: nohup bash gradlew bootRun &

### Testing Application
* curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'
