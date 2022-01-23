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
                        def ejecucion = load 'gradle.groovy'
                        ejecucion.call()
                    } else {
                        println 'ejecutar maven'
                        def ejecucion = load 'maven.groovy'
                        ejecucion.call()
                    }
                }
            }
        }
    }
}