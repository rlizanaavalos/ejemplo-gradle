pipeline {
    agent any

    stages {
        stage('Compile') {
            steps {
                echo 'Compile'
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
                    sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-maven -Dsonar.java.binaries=build"
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
    }
}
