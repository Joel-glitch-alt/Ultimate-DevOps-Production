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
                script {
                    // Build Docker image
                    def img = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                    
                    // Push to Docker Hub
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                        img.push("${DOCKER_TAG}")
                        img.push("latest")
                    }
                    
                    echo "✅ Docker image pushed successfully"
                }
            }
        }
        
    }

    post {
        always {
            echo 'Hello! Pipeline completed.'
            echo "Check your SonarQube dashboard at: http://68.154.50.4:9500"
            echo 'Your Hello World project should now be visible in SonarQube!'
        }
        success {
            echo 'Hello! Pipeline succeeded! 🎉'
        }
        failure {
            echo 'Hello! Pipeline failed, but we tried our best! 😊'
        }
    }
}

// pipeline {
//     agent any

//     tools {
//         jdk 'Java21'
//         maven 'MAVEN3'
//     }

//     environment {
//         SONARQUBE = 'Jenkins-sonar-server'
//         DOCKER_USERNAME = 'addition1905'
//         DOCKER_IMAGE = 'addition1905/java'
//         DOCKER_TAG = "${env.BUILD_NUMBER}"
//     }

//     stages {
//         stage('Clean Workspace') {
//             steps {
//                 cleanWs()
//             }
//         }

//         stage('Checkout SCM') {
//             steps {
//                 checkout scm
//             }
//         }

//         stage('Build') {
//             steps {
//                 sh 'mvn clean package'
//             }
//         }

//         stage('Test') {
//             steps {
//                 sh 'mvn test'
//             }
//             post {
//                 always {
//                     // Archive test results
//                     publishTestResults testResultsPattern: 'target/surefire-reports/TEST-*.xml'
//                     // Archive artifacts
//                     archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
//                 }
//             }
//         }

//         stage('SonarQube Analysis') {
//             steps {
//                 withSonarQubeEnv('Jenkins-sonar-server') {
//                     sh 'mvn sonar:sonar'
//                 }
//             }
//         }

//         // Quality Gate stage removed - SonarQube analysis runs but doesn't block deployment

//         stage('Docker Build & Push') {
//             steps {
//                 script {
//                     try {
//                         // Build with both latest and build number tags
//                         def img = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                        
//                         // Push to Docker Hub
//                         docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
//                             img.push("${DOCKER_TAG}")
//                             img.push("latest")
//                         }
                        
//                         echo "✅ Docker image pushed successfully:"
//                         echo "  - ${DOCKER_IMAGE}:${DOCKER_TAG}"
//                         echo "  - ${DOCKER_IMAGE}:latest"
                        
//                     } catch (Exception e) {
//                         echo "❌ Docker build/push failed: ${e.getMessage()}"
//                         currentBuild.result = 'FAILURE'
//                         error("Docker build/push failed")
//                     }
//                 }
//             }
//         }

//         stage('Deploy') {
//             steps {
//                 script {
//                     echo "✅ Deploying with clean build"
                    
//                     echo "🚀 Deploying Docker container..."
                    
//                     // Example deployment commands
//                     sh """
//                         # Stop existing container if running
//                         docker stop java-app || true
//                         docker rm java-app || true
                        
//                         # Run new container
//                         docker run -d \\
//                             --name java-app \\
//                             -p 8080:8080 \\
//                             ${DOCKER_IMAGE}:${DOCKER_TAG}
//                     """
                    
//                     echo "✅ Application deployed successfully"
//                     echo "🌐 Application should be available at: http://localhost:8080"
//                 }
//             }
//         }
//     }

//     post {
//         always {
//             echo '🏁 Pipeline completed!'
//             echo "📊 Check your SonarQube dashboard at: http://68.154.50.4:9500"
//             echo "🐳 Docker image: ${DOCKER_IMAGE}:${DOCKER_TAG}"
//             echo "Final build result: ${currentBuild.result ?: 'SUCCESS'}"
            
//             // Clean up Docker images to save space
//             sh """
//                 # Remove dangling images
//                 docker image prune -f || true
//             """
//         }
        
//         success {
//             echo '✅ Pipeline succeeded! 🎉'
//             echo "🚀 Application deployed successfully"
//         }
        
//         unstable {
//             echo '⚠️  Pipeline completed but is UNSTABLE'
//         }
        
//         failure {
//             echo '❌ Pipeline failed! 😞'
//             echo "Check the logs above for error details"
//         }
        
//         cleanup {
//             echo '🧹 Cleaning up workspace...'
//             // Additional cleanup if needed
//         }
//     }
// }
