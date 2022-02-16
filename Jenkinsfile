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
                 withSonarQubeEnv('sonarqube-9.3.0.51899') {
                     bat "./gradlew sonarqube"
                 }
             }
        }
        stage("Quality gate") {
             steps {
                 waitForQualityGate abortPipeline: true
             }
        }
	}
}