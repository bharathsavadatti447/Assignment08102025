pipeline {
    agent any

    tools {
        maven "Maven"        // Make sure this matches Jenkins configuration
        jdk "Java-21"        // Make sure this matches Jenkins configuration
    }

    stages {
        stage('Clean Workspace') {
            steps {
                echo "Cleaning Workspace..."
                deleteDir()
            }
        }

        stage('Checkout') {
            steps {
                git branch: "main",
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
                echo "Deploying the Artifact..."
                // Add deploy steps here if needed
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Make sure SonarQube plugin is installed and configured with this exact name
                withSonarqubeEnv('Sonar') {
                    echo "Running SonarQube Analysis..."
                    sh 'mvn sonar:sonar'
                }
            }
        }
    }

    post {
        success {
            echo "✔️BUILD AND TEST STAGE SUCCESSFUL!"
            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/TEST-*.xml'
            archiveArtifacts artifacts: 'target/*.jar', onlyIfSuccessful: true
        }

        failure {
            echo "❌BUILD FAILED!"
            mail to: 'bharath.savadatti447@gmail.com',
                 subject: "Failed: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                 body: "Job '${env.JOB_NAME}' (${env.BUILD_URL}) failed"
        }

        always {
            echo "🔸BUILD EXECUTION COMPLETED🔸"
        }
    }
}
