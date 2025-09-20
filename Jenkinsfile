pipeline {
    agent any

    tools {
        maven 'maven-3.9'
        jdk 'jdk-17'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/nsquezadam/CtaCorriente.git',
                    credentialsId: 'git-credentials'
            }
        }

        stage('Compilar & Test Unitarios') {
            steps {
                bat 'mvn clean test -Dtest=DbConnectionTest'
            }
        }

        stage('Pruebas Funcionales con Cucumber + Selenium') {
            steps {
                bat 'mvn test -Dcucumber.options="--tags @login or @transferencia"'
            }
        }

        stage('Build WAR') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy en Tomcat') {
            steps {
                bat 'copy target\\CtaCorriente.war "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\webapps\\"'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            junit 'target/surefire-reports/*.xml'
        }
    }
}
