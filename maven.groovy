def call(){

stage('compile') {
            steps {
                echo 'compile'
                script {
                    sh './mvnw clean compile -e'
                }
            }
        }
        stage('sonar') {
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
        stage('test') {
            steps {
                echo 'test'
                script {
                    sh './mvnw clean test -e'
                }
            }
        }
        stage('jar') {
            steps {
                echo 'jar'
                script {
                    sh './mvnw clean package -e'
                }
            }
        }
        stage('uploadNexus') {
            steps {
                echo 'uploadNexus'
                script {
                    nexusPublisher nexusInstanceId: 'nexus3', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'build/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '0.0.1']]]
                }
            }
        }
}
return this;

