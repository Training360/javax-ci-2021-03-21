pipeline {
    agent {
        docker { image 'adoptopenjdk:11-jdk-hotspot' }
    }
    stages {
        stage('Build') {
            steps {
                sh './gradlew test assemble'
            }
        }
        stage('Integration test') {
            steps {
                sh './gradlew integrationTest'
            }
        }
    }
}
