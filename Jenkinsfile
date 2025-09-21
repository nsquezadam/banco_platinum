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

    triggers {
        // Ejecutar todos los días a las 12:30
        cron('30 12 * * *')
    }

    stages {
        stage('Etapa 1 - Preparación del proyecto') {
            steps {
                echo '🔹 Clonando repositorio remoto...'
                git branch: "${BRANCH}", url: "${REPO_URL}", credentialsId: 'git-credentials'
                bat 'git rev-parse HEAD'
            }
        }

        stage('Etapa 2 - Construcción del proyecto') {
            steps {
                echo '🔹 Compilando proyecto con Maven...'
                bat 'mvn clean package -DskipTests=true'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.war', fingerprint: true
                }
            }
        }

        stage('Etapa 3 - Pruebas Automatizadas') {
            steps {
                echo '🔹 Ejecutando pruebas con Maven...'
                bat 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Etapa 4 - Publicar WAR en JFrog Artifactory') {
            steps {
                echo '🔹 Publicando artefacto en JFrog...'
                withCredentials([usernamePassword(credentialsId: 'local-jfrog',
                                                 usernameVariable: 'ART_USER',
                                                 passwordVariable: 'ART_PASS')]) {
                    
                    bat 'mvn deploy -s C:\\Users\\nsque\\.m2\\settings.xml'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finalizado. Limpieza del workspace...'
            cleanWs()
        }
    }
}
