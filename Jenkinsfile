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
        MAVEN_OPTS = "-Xmx512m"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/nsquezadam/myconstruction-login.git',
                    credentialsId: 'git-credentials'
            }
        }

        stage('Compilar y Test Unitarios') {
            steps {
                bat 'mvn -B clean test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Pruebas Automatizadas - Cucumber & Selenium') {
            steps {
                // Aquí corremos los features de cucumber
                bat 'mvn verify -Dcucumber.features=src/test/resources/features --plugin pretty'
            }
            post {
                always {
                    junit 'target/cucumber-reports/*.xml'
                }
            }
        }

        stage('Empaquetar WAR') {
            steps {
                bat 'mvn -B package -DskipTests=true'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.war', fingerprint: true
                }
            }
        }
    }
}
