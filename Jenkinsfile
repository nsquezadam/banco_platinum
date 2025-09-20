pipeline {
    agent any

    tools {
        maven 'maven-3.9'     // Ajusta al nombre que configuraste en Jenkins
        jdk   'jdk-17'
    }

    options {
        timestamps()
    }

    environment {
        REPO_URL = 'https://github.com/nsquezadam/banco_platinum.git'
        BRANCH   = 'main'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: "${BRANCH}", url: "${REPO_URL}", credentialsId: 'git-credentials'
                bat 'git rev-parse HEAD'
            }
        }

        stage('Compilar y Test Unitarios') {
            steps {
                bat 'mvn clean test'
            }
            post {
                success {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Pruebas Selenium-Cucumber') {
    steps {
        // Ejecutamos los features de cucumber
        bat 'mvn verify -Dcucumber.plugin="pretty, html:target/cucumber-reports.html, json:target/cucumber.json"'
    }
    post {
        always {
            // Publica el reporte HTML en Jenkins
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target',
                reportFiles: 'cucumber-reports.html',
                reportName: 'Reporte Cucumber'
            ])
        }
    }
}

    }

    post {
        always {
            cleanWs()
        }
    }
}
