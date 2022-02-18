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
        stage('Deploy to Tomcat'){
             bat "copy target\\JenkinsWar.war E:\\Programs\\apache-tomcat-9.0.46\\webapps\\JenkinsWar.war\""
        }
        stage ('Start Tomcat Server') {
              sleep(time:5,unit:"SECONDS")
                 bat "E:\\Programs\\apache-tomcat-9.0.46\\bin\\startup.bat"
                 sleep(time:100,unit:"SECONDS")
        }
    }
}