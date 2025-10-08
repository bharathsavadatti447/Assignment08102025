pipeline {
    agent any

    tools {
        maven "Maven"         // Must match the Maven tool name configured in Jenkins
        jdk "Java-21"         // Must match the JDK tool name configured in Jenkins
    }

    stages {
        stage('Clean Workspace') {
            steps {
                echo "Cleaning Workspace..."
                deleteDir()      // Deletes all files in the workspace before checkout
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
                sh 'mvn clean compile'  // Use 'compile' instead of 'package' for Sonar
            }
        }

        stage('Test') {
            steps {
                echo "Running Maven tests..."
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo "Running SonarQube Analysis..."
                withSonarQubeEnv('Sonar') {   // Name must match your SonarQube server in Jenkins
                    // Change 'app' to your actual source folder
                    sh 'mvn sonar:sonar -Dsonar.projectKey=Assignment08102025 -Dsonar.sources=app -Dsonar.java.binaries=target/classes'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Deploy') {
            steps {
                echo "Deploying the Artifact..."
                archiveArtifacts artifacts: 'target/*.jar', onlyIfSuccessful: true
                // Add actual deployment commands here if needed
            }
        }
    }

    post {
        success {
            echo "✔️ BUILD AND TEST STAGES SUCCESSFUL!"
            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/TEST-*.xml'
        }

        failure {
            echo "❌ BUILD FAILED!"
            mail to: 'bharath.savadatti447@gmail.com',
                 subject: "Failed: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                 body: "Job '${env.JOB_NAME}' (${env.BUILD_URL}) failed. Please check Jenkins logs."
        }

        always {
            echo "🔸 BUILD EXECUTION COMPLETED 🔸"
        }
    }
}
