pipeline {
    agent any

    parameters {
        choice choices: ['gradle', 'maven'], name: 'buildTool'
    }
    stages {
        stage('Pipeline') {
            steps {
                script {
                    print("Pipeline")
                    print params.buildTool
                    if (params.buildTool == 'gradle') {
                        println 'ejeutar gradle'
                    } else {
                        println 'ejecutar maven'
                    }
                }
            }
        }
    }
}
