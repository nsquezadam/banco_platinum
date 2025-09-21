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
                    
                   bat """
        echo ^<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"^> > settings.xml
        echo   ^<servers^> >> settings.xml
        echo     ^<server^> >> settings.xml
        echo       ^<id^>libs-snapshot-local^</id^> >> settings.xml
        echo       ^<username^>%ART_USER%^</username^> >> settings.xml
        echo       ^<password^>%ART_PASS%^</password^> >> settings.xml
        echo     ^</server^> >> settings.xml
        echo     ^<server^> >> settings.xml
        echo       ^<id^>libs-release-local^</id^> >> settings.xml
        echo       ^<username^>%ART_USER%^</username^> >> settings.xml
        echo       ^<password^>%ART_PASS%^</password^> >> settings.xml
        echo     ^</server^> >> settings.xml
        echo   ^</servers^> >> settings.xml
        echo ^</settings^> >> settings.xml

        mvn -B clean deploy --settings settings.xml -DskipTests=true
    """
                }
            }
        }
    }

    post {
		success { echo 'WAR construido y publicado en Artifactory.' }
        always {
            echo 'Pipeline finalizado. Limpieza del workspace...'
            cleanWs()
        }
    }
}
