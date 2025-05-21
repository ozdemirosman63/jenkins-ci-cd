pipeline {
    agent any

    environment {
        REGISTRY = "ozdemirosman"
        REPO = "project4-devops"
        IMAGE_NAME = "${REGISTRY}/${REPO}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/ozdemirosman63/jenkins-ci-cd.git'
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh 'echo $PASS | docker login -u $USER --password-stdin'
                    sh "docker build -t ${IMAGE_NAME}:latest ."
                    sh "docker push ${IMAGE_NAME}:latest"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s/service.yaml'
                sh 'kubectl apply -f k8s/deployment.yaml'
            }
        }
    }
}
