pipeline {
    agent any

    tools {
        jdk 'Java21'
        maven 'MAVEN3'
    }

    stages {
        stage('Clean up workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout code from SCM') {
            steps {
                checkout scm
            }
        }

        stage('Build application') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test application') {
            steps {
                sh 'mvn test'
            }
        }
    }
}
