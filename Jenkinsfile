

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
}





