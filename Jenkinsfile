pipeline {
    agent any

    stages {
        stage('Compile') {
            steps {
                echo 'Compile-'
                script {
                    sh 'chmod a+x gradlew'
                    sh './gradlew build'
                }
            }
        }
        stage('SonarQube analysis') {
            steps {
                script {
                // requires SonarQube Scanner 2.8+
                scannerHome = tool 'sonar-scanner'
                }
                withSonarQubeEnv('Sonarqube-server') {
                    sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle2 -Dsonar.java.binaries=build"
                }
            }
        }
        stage('Run') {
            steps {
                echo 'Run'
                script {
                    sh 'nohup bash gradlew bootRun &'
                    sleep(30)
                }
            }
        }
        stage('TestApp') {
            steps {
                echo 'TestApp'
                script {
                    sh """curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'"""
                }
            }
        }
        stage('nexus') {
            steps {
                echo 'Nexus'
                script {
                    nexusPublisher nexusInstanceId: 'nexus3', nexusRepositoryId: 'test-gradle', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'build/libs/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'ejemplo-maven-feature-sonar', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]
                }
            }
        }
    }
}
