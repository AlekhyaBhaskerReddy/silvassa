pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/AlekhyaBhaskerReddy/silvassa.git' // Your repository
            }
        }
        stage('Install Dependencies') {
            steps {
                sh 'mvn install -DskipTests' // Install dependencies, skipping tests
            }
        }
        stage('Run Selenium Tests') {
    steps {
        sh 'mvn test -Dbrowser=firefox' // pass the browser argument if needed
    }
}
        }
    }

    post {
        always {
            // Collect test reports
            junit '**/target/surefire-reports/*.xml' // Adjust the path if needed
            // Archive build artifacts, such as JAR files
            archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
        }
        success {
            echo 'Build and tests were successful!'
        }
        failure {
            echo 'Build or tests failed!'
        }
    }
}
