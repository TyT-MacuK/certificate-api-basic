pipeline {
    agent any

    stages {
        stage('Test') {
            steps {
                bat "./gradlew test"
            }
        }
        stage('Build') {
            steps {
                bat "./gradlew build"
            }
        }
    }
}