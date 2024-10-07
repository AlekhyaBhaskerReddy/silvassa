pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/AlekhyaBhaskerReddy/silvassa.git' // Replace with your actual repository
            }
        }
        stage('Install Dependencies') {
            steps {
                sh 'mvn install -DskipTests'
            }
        }
        stage('Run Selenium Tests') {
            steps {
                sh 'mvn test'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml' // Adjust the path based on your project structure
            archiveArtifacts '**/target/*.jar' // Archive any necessary artifacts
        }
    }
}
