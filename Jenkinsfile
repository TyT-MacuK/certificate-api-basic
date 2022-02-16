pipeline {
	agent any
	stages {
		stage('Build') {
            steps {
                bat './gradlew build'
            }
        }

        stage('Test') {
            steps {
                bat './gradlew test'
            }
        }

        stage('Check') {
            steps {
                bat './gradlew check'
            }
        }
        stage('SonarQube analysis') {
             steps {
                 withSonarQubeEnv(installationName: 'SonarQube') {
                     bat "./gradlew sonarqube"
                 }
             }
        }
        stage("Quality gate") {
             steps {
                 timeout(time: 2, unit: 'MINUTES') {
                     waitForQualityGate abortPipeline: true
                 }
             }
        }
	}
}