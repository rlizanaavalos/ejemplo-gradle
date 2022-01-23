def call(){
    stage('compile') {
        sh './mvnw clean compile -e'
    }
    stage('sonar') {
        scannerHome = tool 'sonar-scanner'
        withSonarQubeEnv('Sonarqube-server') {
            sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-maven -Dsonar.java.binaries=build"
        }
    }
    stage('test') {
        sh './mvnw clean test -e'
    }
    stage('jar') {
        sh './mvnw clean package -e'
    }
    stage('Run') {
        sh 'nohup bash mvnw spring-boot:run &'
        sleep(30)
    }
    stage('TestApp') {
        sh """curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'"""
    }
    stage('uploadNexus') {
        nexusPublisher nexusInstanceId: 'nexus3', nexusRepositoryId: 'test-gradle', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'build/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'ejemplo-maven-feature-sonar', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]
    }
}
return this;

