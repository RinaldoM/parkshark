pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'jdk-12.0.2'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn -f digibooky/pom.xml clean install'
            }
        }
    }

    post {
            always {
                publishCoverage adapters: [jacocoAdapter('**/target/site/jacoco/jacoco.xml')]
                junit '**/target/surefire-reports/*.xml'
            }
    }
}