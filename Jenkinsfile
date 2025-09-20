pipeline {
    agent any

    tools {
        maven 'maven-3.9'
        jdk   'jdk-17'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/nsquezadam/myconstruction-login.git', credentialsId: 'git-credentials'
            }
        }

        stage('Build WAR') {
            steps {
                bat 'mvn clean package -DskipTests=false'
            }
        }

        stage('Pruebas Automatizadas') {
            steps {
                bat 'mvn test'
            }
            post {
                always {
                    archiveArtifacts artifacts: 'target/cucumber-report.html', fingerprint: true
                }
            }
        }
    }
}
