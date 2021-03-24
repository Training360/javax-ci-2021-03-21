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
                sh './gradlew -PbuildNumber=$BUILD_NUMBER  test assemble'
            }
        }
        /*stage('Integration test') {
            steps {
                sh './gradlew integrationTest'
            }
        }*/
/*        stage('Integration test on MariaDB') {
                    steps {
                        sh './gradlew -Pspring.datasource.url=jdbc:mariadb://employees-it-mariadb/employees -Pspring.datasource.username=employees -Pspring.datasource.password=employees integrationTest'
                    }
        }*/
        /*stage('SonarQube') {
            steps {
                sh './gradlew -PbuildNumber=$BUILD_NUMBER  sonarqube -Dsonar.userHome=/tmp/.sonar -Dsonar.host.url=http://employees-sonarqube:9000'
            }
        }*/
        stage('Publish') {
            steps {
                sh './gradlew -PbuildNumber=${BUILD_NUMBER} -PnexusUrl=http://employees-nexus:8081 -PnexusUsername=admin -PnexusPassword=admin publish'
            }
        }

    }
}
