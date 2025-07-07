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

//     parameters {
//         choice(name: 'ENV', choices: ['dev', 'staging', 'production'], description: 'Choose environment')
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
//         }

//         stage('SonarQube Analysis') {
//             steps {
//                 withSonarQubeEnv("${SONARQUBE}") {
//                     sh 'mvn sonar:sonar'
//                 }
//             }
//         }

//         stage('Docker Build & Push') {
//             steps {
//                 retry(3) {
//                     timeout(time: 10, unit: 'MINUTES') {
//                         script {
//                             def img = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
//                             docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
//                                 img.push("${DOCKER_TAG}")
//                                 img.push("latest")
//                             }
//                             echo "✅ Docker image pushed successfully"
//                         }
//                     }
//                 }
//             }
//         }

//         stage('Security Scan - Trivy') {
//             steps {
//                 timeout(time: 20, unit: 'MINUTES') {
//                     script {
//                         sh '''
//                             echo "🔍 Starting Trivy vulnerability scan with optimized settings..."

//                             mkdir -p trivy-report

//                             # Run Trivy scan with proper database handling
//                             docker run --rm \
//                               -v /var/run/docker.sock:/var/run/docker.sock \
//                               -v $(pwd)/trivy-report:/root/reports \
//                               -v trivy-cache:/root/.cache/trivy \
//                               aquasec/trivy:latest image \
//                               --timeout 15m \
//                               --exit-code 0 \
//                               --format template \
//                               --template "@contrib/html.tpl" \
//                               --output /root/reports/report.html \
//                               --severity CRITICAL,HIGH \
//                               ${DOCKER_IMAGE}:${BUILD_NUMBER}
//                         '''
//                     }
//                 }
//             }
//         }

//         stage('Deploy') {
//             steps {
//                 script {
//                     sh "ENV=${params.ENV} BUILD_NUMBER=${env.BUILD_NUMBER} ./deploy.sh"
//                 }
//             }
//         }
//     }

//     post {
//         always {
//             echo 'Hello! Pipeline completed.'
//             echo "Check your SonarQube dashboard at: http://localhost:9000/"
//             archiveArtifacts artifacts: 'trivy-report/report.html', fingerprint: true, allowEmptyArchive: true
//         }
       
//         success {
//                 echo '✅ Pipeline succeeded! 🎉'

//                     script {
//         // 1. Publish the Trivy report (same as before)
//                      if (fileExists('trivy-report/report.html')) {
//                      publishHTML(target: [
//                      reportDir: 'trivy-report',
//                      reportFiles: 'report.html',
//                      reportName: 'Trivy Vulnerability Report',
//                      keepAll: true,
//                      alwaysLinkToLastBuild: true
//             ])
//         } else {
//             echo "⚠️ Trivy HTML report not found, skipping publish."
//         }

//         // 2. Get Git committer email
//         def authorEmail = sh(script: 'git log -1 --pretty=format:"%ae"', returnStdout: true).trim()

//         // 3. Email the committer
//         emailext(
//             to: "${authorEmail}",
//             subject: "✅ SonarQube Report for ${env.JOB_NAME} #${env.BUILD_NUMBER}",
//             body: """<p>Hello,</p>
//                      <p>Your code was analyzed successfully by SonarQube.</p>
//                      <p><a href='http://localhost:9000/dashboard?id=${env.JOB_NAME}'>Click to see SonarQube results</a></p>
//                      <p>Check Trivy Report (if applicable): <a href='${env.BUILD_URL}Trivy%20Vulnerability%20Report/'>Click Here</a></p>
//                      <p>Thanks,<br/>Jenkins</p>""",
//             mimeType: 'text/html'
//                       )
//                   }
//          }

//         failure {
//             echo 'Hello! Pipeline failed, but you tried your best! 😊'
//         }
//     }
// }



// Adding Email Notification for SonarQube Report
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
                withSonarQubeEnv("${SONARQUBE}") {
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
                timeout(time: 20, unit: 'MINUTES') {
                    script {
                        sh '''
                            echo "🔍 Starting Trivy vulnerability scan with optimized settings..."

                            mkdir -p trivy-report

                            # Run Trivy scan with proper database handling
                            docker run --rm \
                              -v /var/run/docker.sock:/var/run/docker.sock \
                              -v $(pwd)/trivy-report:/root/reports \
                              -v trivy-cache:/root/.cache/trivy \
                              aquasec/trivy:latest image \
                              --timeout 15m \
                              --exit-code 0 \
                              --format template \
                              --template "@contrib/html.tpl" \
                              --output /root/reports/report.html \
                              --severity CRITICAL,HIGH \
                              ${DOCKER_IMAGE}:${BUILD_NUMBER}
                        '''
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
            archiveArtifacts artifacts: 'trivy-report/report.html', fingerprint: true, allowEmptyArchive: true
        }
       
        success {
            echo '✅ Pipeline succeeded! 🎉'
            script {
                try {
                    // 1. Publish the Trivy report
                    if (fileExists('trivy-report/report.html')) {
                        publishHTML(target: [
                            reportDir: 'trivy-report',
                            reportFiles: 'report.html',
                            reportName: 'Trivy Vulnerability Report',
                            keepAll: true,
                            alwaysLinkToLastBuild: true
                        ])
                    } else {
                        echo "⚠️ Trivy HTML report not found, skipping publish."
                    }

                    // 2. Debug Git information
                    echo "=== DEBUG: Git Information ==="
                    sh 'git log -1 --pretty=format:"Author: %an <%ae>" || echo "No git log available"'
                    sh 'git config user.email || echo "No git user.email configured"'
                    
                    // 3. Get Git committer email with better error handling
                    def authorEmail = ""
                    try {
                        authorEmail = sh(script: 'git log -1 --pretty=format:"%ae"', returnStdout: true).trim()
                        echo "DEBUG: Retrieved author email: '${authorEmail}'"
                    } catch (Exception e) {
                        echo "ERROR: Could not retrieve git author email: ${e.getMessage()}"
                        // Fallback to a default email or use BUILD_USER_EMAIL if available
                        authorEmail = env.BUILD_USER_EMAIL ?: "admin@example.com"
                        echo "DEBUG: Using fallback email: '${authorEmail}'"
                    }

                    // 4. Validate email
                    if (!authorEmail || authorEmail.isEmpty()) {
                        echo "ERROR: Author email is empty or null"
                        authorEmail = "admin@example.com" // Replace with your email
                        echo "DEBUG: Using default email: '${authorEmail}'"
                    }

                    // 5. Send email with debug info
                    echo "DEBUG: Attempting to send email to: ${authorEmail}"
                    emailext(
                        to: "${authorEmail}",
                        subject: "✅ SonarQube Report for ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                        body: """<p>Hello,</p>
                                 <p>Your code was analyzed successfully by SonarQube.</p>
                                 <p><strong>Build Details:</strong></p>
                                 <ul>
                                     <li>Job: ${env.JOB_NAME}</li>
                                     <li>Build: #${env.BUILD_NUMBER}</li>
                                     <li>Status: SUCCESS</li>
                                     <li>Timestamp: ${new Date()}</li>
                                 </ul>
                                 <p><a href='http://localhost:9000/dashboard?id=${env.JOB_NAME}'>Click to see SonarQube results</a></p>
                                 <p>Check Trivy Report (if applicable): <a href='${env.BUILD_URL}Trivy%20Vulnerability%20Report/'>Click Here</a></p>
                                 <p>Thanks,<br/>Jenkins</p>""",
                        mimeType: 'text/html'
                    )
                    echo "DEBUG: Email sent successfully"
                    
                } catch (Exception e) {
                    echo "ERROR: Failed to send email: ${e.getMessage()}"
                    echo "ERROR: Stack trace: ${e.getStackTrace()}"
                }
            }
        }

        failure {
            echo 'Hello! Pipeline failed, but you tried your best! 😊'
            script {
                // Also try to send failure notification
                try {
                    def authorEmail = sh(script: 'git log -1 --pretty=format:"%ae"', returnStdout: true).trim()
                    if (authorEmail) {
                        emailext(
                            to: "${authorEmail}",
                            subject: "❌ Pipeline Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                            body: """<p>Hello,</p>
                                     <p>Your pipeline failed. Please check the logs.</p>
                                     <p><a href='${env.BUILD_URL}'>View Build Logs</a></p>
                                     <p>Thanks,<br/>Jenkins</p>""",
                            mimeType: 'text/html'
                        )
                    }
                } catch (Exception e) {
                    echo "Could not send failure email: ${e.getMessage()}"
                }
            }
        }
    }
}