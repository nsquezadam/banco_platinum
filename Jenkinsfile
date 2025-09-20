pipeline {
    agent any

    tools {
        // Configura estos nombres en Manage Jenkins → Global Tool Configuration
        maven 'maven-3.9'
        jdk   'jdk-17'
    }

    environment {
        MAVEN_OPTS = "-Xmx512m"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', 
                    url: 'https://github.com/nsquezadam/banco_platinum.git', 
                    credentialsId: 'git-credentials'
            }
        }

        stage('Build') {
            steps {
                echo "📦 Compilando y ejecutando pruebas unitarias con Maven..."
                bat 'mvn clean test'
            }
        }

        stage('Package WAR') {
            steps {
                echo "⚙️ Empaquetando WAR del proyecto..."
                bat 'mvn package -DskipTests=true'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.war', fingerprint: true
                }
            }
        }

        stage('Pruebas Automatizadas') {
            steps {
                echo "🤖 Ejecutando pruebas Cucumber + Selenium..."
                bat 'mvn test -Dcucumber.plugin="html:target/cucumber-report.html"'
            }
            post {
                always {
                    echo "📑 Archivando reporte de Cucumber"
                    archiveArtifacts artifacts: 'target/cucumber-report.html', fingerprint: true
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline finalizado (con éxito o con errores)"
        }
    }
}
