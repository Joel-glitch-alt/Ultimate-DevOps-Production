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

       
              stage('Security Scan - Trivy') {
                      steps {
                       script {
                         sh '''
                      echo "🔍 Starting Trivy vulnerability scan using Docker..."

                      docker run --rm \
                      -v /var/run/docker.sock:/var/run/docker.sock \
                      aquasec/trivy:latest image \
                    --exit-code 1 \
                    --severity CRITICAL,HIGH \
                    addition1905/java:${BUILD_NUMBER}
                   '''
                 }
             }
          }

          //////////////
//            stage('Trivy Docker Image Scan') {
//       steps {
//         script {
//           // Scan without failing the build, but report issues
//           def dockerScanResult = sh(script: "trivy image --severity HIGH,CRITICAL --format table ${DOCKER_IMAGE}", returnStatus: true)
//           if (dockerScanResult != 0) {
//             currentBuild.result = 'UNSTABLE'
//             echo "Security vulnerabilities found in Docker image scan."
//             // Generate detailed report
//             sh "trivy image --severity HIGH,CRITICAL --format json --output trivy-docker-report.json ${DOCKER_IMAGE}"
//           }
//         }
//       }
//       post {
//         always {
//           // Archive the scan results
//           archiveArtifacts artifacts: 'trivy-docker-report.json', allowEmptyArchive: true
//         }
//       }
//     }
//   }
//////////////////////


        //
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