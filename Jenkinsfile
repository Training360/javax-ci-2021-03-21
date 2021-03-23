pipeline {
    agent {
        docker {
            image 'adoptopenjdk:11-jdk-hotspot'
            args '--network jenkins'
        }
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
        stage('Integration test on MariaDB') {
                    steps {
                        sh './gradlew -Pspring.datasource.url=jdbc:mariadb://employees-it-mariadb/employees -Pspring.datasource.username=employees -Pspring.datasource.password=employees integrationTest'
                    }
        }
    }
}
