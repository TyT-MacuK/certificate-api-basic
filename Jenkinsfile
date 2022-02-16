pipeline {
	agent any
	stages {
		stage('One') {
			steps {
				echo 'First step'
			}
		}

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

		stage('Five') {
			steps {
				echo 'Finished'
			}
		}
	}
}