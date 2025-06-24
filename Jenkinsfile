pipeline{
    agent any

    tools{
        jdk "Java17"
        maven "MAVEN3"
    }

    stages{
        stage("Clean up workspace"){
            steps{
                cleanWs()
            }
            
        }

        stage("Checkout code from SCM"){
            steps{
                checkout scm
            }
            
        }

          stage("Compile and test"){
            steps{
                sh "mvn clean compile test"
            }
            
        }
    }
    
}