pipeline {
    agent any

    tools {
        maven 'maven-3.9'   
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
               
                bat 'mvn verify'
            }
            post {
                always {
                    publishHTML([
                        reportDir: 'target/cucumber-html-reports',
                        reportFiles: 'index.html',    
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
