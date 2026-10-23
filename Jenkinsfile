// CI: format check + tests (incl. module boundaries) → image → push. Argo CD deploys from the manifests repo.
pipeline {
    agent any

    environment {
        IMAGE = "${env.REGISTRY ?: 'registry.example.com'}/hrd/application-logic-service"
        TAG = "${env.GIT_COMMIT?.take(7) ?: 'latest'}"
    }

    options {
        timeout(time: 30, unit: 'MINUTES')
        disableConcurrentBuilds()
    }

    stages {
        stage('Check') {
            steps {
                sh './gradlew --no-daemon check'
            }
            post {
                always {
                    junit 'build/test-results/test/*.xml'
                    archiveArtifacts artifacts: 'build/reports/jacoco/test/jacocoTestReport.xml', allowEmptyArchive: true
                }
            }
        }

        stage('Build image') {
            when { branch 'main' }
            steps {
                sh 'docker build -t $IMAGE:$TAG .'
            }
        }

        stage('Push image') {
            when { branch 'main' }
            steps {
                withCredentials([usernamePassword(credentialsId: 'registry-credentials', usernameVariable: 'REG_USER', passwordVariable: 'REG_PASS')]) {
                    sh 'echo "$REG_PASS" | docker login -u "$REG_USER" --password-stdin ${IMAGE%%/*}'
                    sh 'docker push $IMAGE:$TAG'
                }
            }
        }
    }
}
