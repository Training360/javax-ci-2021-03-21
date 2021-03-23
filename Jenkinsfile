pipeline {
    agent {
        docker { image 'adoptopenjdk:11-jdk-hotspot' }
    }
    stages {
        stage('Test') {
            steps {
                sh './gradlew test assemble'
            }
        }
    }
}
