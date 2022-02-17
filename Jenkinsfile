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

        stage('Jacoco') {
            steps {
                bat "./gradlew jacocoTestReport"
                bat "./gradlew codeCoverageReport"
            }
        }
        stage('SonarQube analysis') {
             steps {
                 withSonarQubeEnv(installationName: 'sq1') {
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

	post {
            success {
                deploy adapters: [
                                    tomcat9(url: 'http://localhost:8080')
                                 ],
                                 war: '**/*.war',
                                 contextPath: 'app'
            }
        }
}