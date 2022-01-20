pipeline {
    agent any

    stages {
        stage('Compile') {
            steps {
                echo 'Compile'
                script {
                    sh './mvnw clean compile -e'
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
        stage('Test') {
            steps {
                echo 'Test'
                script {
                    sh './mvnw clean test -e'
                }
            }
        }
        stage('Package') {
            steps {
                echo 'Package'
                script {
                    sh './mvnw clean package -e'
                }
            }
        }
        stage('Run') {
            steps {
                echo 'Run'
                script {
                    sh 'nohup bash mvnw spring-boot:run &'
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
