pipeline {
    agent any

    environment {
        REGISTRY = "ozdemirosman"
        REPO = "project4-devops"
    }

    stages {
        stage('Get Git Commit SHA') {
            steps {
                script {
                    env.IMAGE_TAG = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
                    env.DOCKER_IMAGE = "${env.REGISTRY}/${env.REPO}:${env.IMAGE_TAG}"
                }
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${env.DOCKER_IMAGE} ."
            }
        }

        stage('DockerHub Login') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh 'echo $PASSWORD | docker login -u $USERNAME --password-stdin'
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                sh "docker push ${env.DOCKER_IMAGE}"
            }
        }

        stage('Generate deployment.yaml') {
            steps {
                sh 'sed "s|IMAGE_TAG|${IMAGE_TAG}|" k8s/deployment-template.yaml > deployment.yaml'
            }
        }

        stage('Deploy to K8s') {
            steps {
                sh 'kubectl apply -f deployment.yaml'
                sh 'kubectl apply -f service.yaml'
            }
        }
    }
}
