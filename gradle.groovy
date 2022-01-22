def call(){
    stage('Compile') {
        sh 'chmod a+x gradlew'
        sh './gradlew build'
    }
    stage('SonarQube analysis') {
        scannerHome = tool 'sonar-scanner'
        withSonarQubeEnv('Sonarqube-server') {
            sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle2 -Dsonar.java.binaries=build"
        }
    }
    stage('Run') {
        sh 'nohup bash gradlew bootRun &'
        sleep(30)
    }
    stage('TestApp') {
        sh """curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'"""
    }
    stage('uploadNexus') {
        nexusPublisher nexusInstanceId: 'nexus3', nexusRepositoryId: 'test-gradle', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'build/libs/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'ejemplo-maven-feature-sonar', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]
    }
}

return this;
