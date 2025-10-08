pipeline {
    agent any

    tools {
        maven "Maven"
        jdk "Java-21"
    }

    stages {
        stage('Clean Workspace') {
            steps {
                echo "Cleaning Workspace......"
                deleteDir()
            }
        }

        stage('Checkout') {
            steps {
                git branch: "main"
                url: 'https://github.com/bharathsavadatti447/Assignment08102025.git'
            }
        }

        stage('Build') {
            steps {
                echo "Compiling Maven project..."
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                echo "Running Maven tests..."
                sh 'mvn test'
            }
        }    

        stage('Deploy') {
            steps {
                echo "Deploy the Artifact..."

            }
        }
        stage ('SonarQube Analysis') {
            steps {
                withSonarqubeEnv('Sonarqube'){
                    sh 'chmod 777 sonar-project'
                    sh 'sonar-project'
                }
            }
        }
    } // End of stages

    post {
        success {
            echo "✔️BUILD AND TEST STAGE SUCCESSFUL...!!!"
            // Updated: allow empty results to prevent failure if no tests
            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/TEST-*.xml'
            archiveArtifacts artifacts: 'target/*.jar', onlyIfSuccessful: true
        }

        failure {
            echo "❌Failure..!!!"
            mail to: 'bharath.savadatti447@gmail.com',
                 subject: "Failed: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                 body: "Job '${env.JOB_NAME}' (${env.BUILD_URL}) failed"
        }

        always {
            echo "🔸🔸🔸You are executed the build🔸🔸🔸!!!"
        }
    }
}