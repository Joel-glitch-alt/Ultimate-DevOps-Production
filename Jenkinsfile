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
        // success {
        //     echo 'Hello! Pipeline succeeded! 🎉'
        //     script {
        //         if (fileExists('trivy-report/report.html')) {
        //             publishHTML(target: [
        //                 reportDir: 'trivy-report',
        //                 reportFiles: 'report.html',
        //                 reportName: 'Trivy Vulnerability Report',
        //                 keepAll: true,
        //                 alwaysLinkToLastBuild: true
        //             ])
        //         } else {
        //             echo "⚠️ Trivy HTML report not found, skipping publish."
        //         }
        //     }
        // }
        success {
                echo '✅ Pipeline succeeded! 🎉'

                    script {
        // 1. Publish the Trivy report (same as before)
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

        // 2. Get Git committer email
        def authorEmail = sh(script: 'git log -1 --pretty=format:"%ae"', returnStdout: true).trim()

        // 3. Email the committer
        emailext(
            to: "${authorEmail}",
            subject: "✅ SonarQube Report for ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            body: """<p>Hello,</p>
                     <p>Your code was analyzed successfully by SonarQube.</p>
                     <p><a href='http://localhost:9000/dashboard?id=${env.JOB_NAME}'>Click to see SonarQube results</a></p>
                     <p>Check Trivy Report (if applicable): <a href='${env.BUILD_URL}Trivy%20Vulnerability%20Report/'>Click Here</a></p>
                     <p>Thanks,<br/>Jenkins</p>""",
            mimeType: 'text/html'
                      )
                  }
         }

        failure {
            echo 'Hello! Pipeline failed, but you tried your best! 😊'
        }
    }
}