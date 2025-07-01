pipeline {
    agent any
    
    tools {
        jdk 'Java21'
        maven 'MAVEN3'
    }
    
    environment {
        SONARQUBE = 'Jenkins-sonar-server'
        DOCKER_USERNAME = 'addition1905'
        DOCKER_IMAGE = 'addition1905/java'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }

     parameters {
    choice(name: 'ENV', choices: ['dev', 'staging', 'production'], description: 'Choose environment')
    }
    
    stages {
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }
        
        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('Jenkins-sonar-server') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        
        stage('Docker Build & Push') {
            steps {
                retry(3) {
                    timeout(time: 10, unit: 'MINUTES') {
                        script {
                            def img = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                            docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                                img.push("${DOCKER_TAG}")
                                img.push("latest")
                            }
                            echo "✅ Docker image pushed successfully"
                        }
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
            script {
                sh "ENV=${params.ENV} BUILD_NUMBER=${env.BUILD_NUMBER} ./deploy.sh"
            }
            }
        }
    }
    post {
        always {
            echo 'Hello! Pipeline completed.'
            echo "Check your SonarQube dashboard at: http://localhost:9000/"
            echo 'Your Hello World project should now be visible in SonarQube!'
        }
        success {
            echo 'Helo! Pipeline succeeded! 🎉'
        }
        failure {
            echo 'Hello! Pipeline failed, but you tried your best! 😊'
        }
    }
}