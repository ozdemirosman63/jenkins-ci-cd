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

        stage('Get Git Commit SHA') {
            steps {
                script {
                    env.IMAGE_TAG = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
                    env.DOCKER_IMAGE = "${REGISTRY}/${REPO}:${IMAGE_TAG}"
                }
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${IMAGE_NAME} ."
                sh "docker build -t ${DOCKER_IMAGE} ."
            }
        }

        stage('Docker Login & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh 'echo $PASS | docker login -u $USER --password-stdin'
                    sh "docker push ${IMAGE_NAME}"
                    sh "docker push ${DOCKER_IMAGE}"
                }
            }
        }

        stage('Generate Deployment YAML') {
            steps {
                sh 'sed "s|IMAGE_TAG|${IMAGE_TAG}|" k8s/deployment-template.yaml > deployment.yaml'
            }
        }

        stage('K8s Deploy') {
            steps {
                sh 'kubectl apply -f deployment.yaml'
                sh 'kubectl apply -f k8s/service.yaml'
            }
        }
    }
}
