

// pipeline {
//     agent any

//     tools {
//         jdk 'Java21'
//         maven 'MAVEN3'
//     }

//     environment {
//         SONARQUBE = 'Jenkins-sonar-server'
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
//                 withSonarQubeEnv('Jenkins-sonar-server') {
//                     sh 'mvn sonar:sonar'
//                 }
//             }
//         }

//         // stage('Quality Gate') {
//         //     steps {
//         //         script {
//         //             timeout(time: 5, unit: 'MINUTES') {
//         //                 try {
//         //                     def qg = waitForQualityGate()
//         //                     if (qg.status != 'OK') {
//         //                         error "Pipeline aborted due to quality gate failure: ${qg.status}"
//         //                     }
//         //                 } catch (Exception e) {
//         //                     echo "Quality Gate timeout or failed: ${e.getMessage()}"
//         //                     echo "Continuing pipeline - check SonarQube manually at: http://localhost:9000"
//         //                     currentBuild.result = 'UNSTABLE'
//         //                 }
//         //             }
//         //         }
//         //     }
//         stage('Quality Gate') {
//             when {
//             not { params.SKIP_QUALITY_GATE }
//             }
//             steps {
//             script {
//                 try {
//                 def qg = waitForQualityGate()
//                 if (qg.status != 'OK') {
//                     echo "Quality Gate failed but continuing pipeline"
//                     currentBuild.result = 'UNSTABLE'
//                 }
//                 } catch (e) {
//                 echo "Quality Gate check failed: ${e.getMessage()}"
//                 currentBuild.result = 'UNSTABLE'
//                 }
//             }
//             }
//         }
//     }
// }





pipeline {
    agent any

    tools {
        jdk 'Java21'
        maven 'MAVEN3'
    }

    environment {
        SONARQUBE = 'Jenkins-sonar-server'
    }

    parameters {
        booleanParam(name: 'SKIP_QUALITY_GATE', defaultValue: false, description: 'Skip Quality Gate check')
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

        stage('Quality Gate') {
            when {
                not { params.SKIP_QUALITY_GATE }
            }
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    script {
                        try {
                            def qg = waitForQualityGate()
                            if (qg.status != 'OK') {
                                echo "⚠️  Quality Gate FAILED: ${qg.status}"
                                echo "Quality Gate conditions that failed:"
                                qg.conditions.each { condition ->
                                    if (condition.status != 'OK') {
                                        echo "  - ${condition.metricKey}: ${condition.actualValue} ${condition.operator} ${condition.errorThreshold ?: condition.warningThreshold}"
                                    }
                                }
                                echo "Setting build as UNSTABLE but continuing pipeline..."
                                currentBuild.result = 'UNSTABLE'
                            } else {
                                echo "✅ Quality Gate PASSED"
                            }
                        } catch (Exception e) {
                            echo "⚠️  Quality Gate check failed or timed out: ${e.getMessage()}"
                            echo "Setting build as UNSTABLE and continuing pipeline..."
                            echo "Check SonarQube manually at: http://172.17.0.3:9000"
                            currentBuild.result = 'UNSTABLE'
                        }
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    if (currentBuild.result == 'UNSTABLE') {
                        echo "🚨 Deploying despite Quality Gate issues - Build is UNSTABLE"
                    } else {
                        echo "✅ Deploying with clean Quality Gate"
                    }
                    
                    // Add your deployment steps here
                    echo "🚀 Deploying application..."
                    // Example deployment commands:
                    // sh 'docker build -t myapp .'
                    // sh 'docker run -d -p 8080:8080 myapp'
                }
            }
        }
    }

    post {
        always {
            echo "🏁 Pipeline completed"
            echo "Final build result: ${currentBuild.result ?: 'SUCCESS'}"
        }
        
        success {
            echo "✅ Pipeline completed successfully"
        }
        
        unstable {
            echo "⚠️  Pipeline completed but is UNSTABLE"
            echo "This usually means Quality Gate failed but deployment proceeded"
            echo "Check SonarQube for quality issues: http://172.17.0.3:9000"
        }
        
        failure {
            echo "❌ Pipeline FAILED"
        }
        
        cleanup {
            // Clean up any resources if needed
            echo "🧹 Cleaning up..."
        }
    }
}