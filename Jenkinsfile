pipeline {
    agent any

    tools {
        jdk 'Java21'
        maven 'MAVEN3'
    }

    environment {
        SONARQUBE = 'Jenkins-sonar-server'
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

    //     stage('SonarQube Analysis') {
    //         steps {
    //             withSonarQubeEnv('Jenkins-sonar-server') {
    //                 sh 'mvn sonar:sonar'
    //             }
    //         }
    //     }
    // }
}



// pipeline {
//     agent any

//     tools {
//         jdk 'Java21'
//         maven 'MAVEN3'
//     }

//     stages {
//         stage('Clean up workspace') {
//             steps {
//                 cleanWs()
//             }
//         }

//         stage('Checkout code from SCM') {
//             steps {
//                 checkout scm
//             }
//         }

//         stage('Build application') {
//             steps {
//                 sh 'mvn clean package'
//             }
//         }

//         stage('Test application') {
//             steps {
//                 sh 'mvn test'
//             }
//         }

//         stage('SonarQube Analysis') {
//             steps {
//                 script {
//                     try {
//                         withSonarQubeEnv('Jenkins-sonar-server') {
//                             sh 'mvn sonar:sonar'
//                         }
//                     } catch (Exception e) {
//                         echo "SonarQube analysis failed: ${e.getMessage()}"
//                         echo "Please check if SonarQube server is running and accessible"
//                         // Mark as unstable instead of failing the entire pipeline
//                         currentBuild.result = 'UNSTABLE'
//                     }
//                 }
//             }
//         }

//         stage('Quality Gate') {
//             steps {
//                 script {
//                     if (currentBuild.result != 'UNSTABLE') {
//                         try {
//                             timeout(time: 1, unit: 'HOURS') {
//                                 waitForQualityGate abortPipeline: true
//                             }
//                         } catch (Exception e) {
//                             echo "Quality Gate check failed or timed out: ${e.getMessage()}"
//                             currentBuild.result = 'UNSTABLE'
//                         }
//                     } else {
//                         echo "Skipping Quality Gate due to SonarQube analysis failure"
//                     }
//                 }
//             }
//         }
//     }

//     post {
//         always {
//             echo 'Pipeline completed!'
//         }
//         success {
//             echo 'Pipeline executed successfully!'
//         }
//         unstable {
//             echo 'Pipeline completed with warnings (SonarQube issues)'
//         }
//         failure {
//             echo 'Pipeline failed!'
//         }
//     }
// }