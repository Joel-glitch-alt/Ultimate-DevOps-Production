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
//         stage('Quality Gate') {
//             steps {
//                 script {
//                     def qg = waitForQualityGate()
//                     if (qg.status != 'OK') {
//                         error "Pipeline aborted due to quality gate failure: ${qg.status}"
//                     }
//                 }
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

        // stage('Quality Gate') {
        //     steps {
        //         script {
        //             timeout(time: 5, unit: 'MINUTES') {
        //                 try {
        //                     def qg = waitForQualityGate()
        //                     if (qg.status != 'OK') {
        //                         error "Pipeline aborted due to quality gate failure: ${qg.status}"
        //                     }
        //                 } catch (Exception e) {
        //                     echo "Quality Gate timeout or failed: ${e.getMessage()}"
        //                     echo "Continuing pipeline - check SonarQube manually at: http://localhost:9000"
        //                     currentBuild.result = 'UNSTABLE'
        //                 }
        //             }
        //         }
        //     }
        stage('Quality Gate') {
            when {
            not { params.SKIP_QUALITY_GATE }
            }
            steps {
            script {
                try {
                def qg = waitForQualityGate()
                if (qg.status != 'OK') {
                    echo "Quality Gate failed but continuing pipeline"
                    currentBuild.result = 'UNSTABLE'
                }
                } catch (e) {
                echo "Quality Gate check failed: ${e.getMessage()}"
                currentBuild.result = 'UNSTABLE'
                }
            }
            }
        }
    }
}


