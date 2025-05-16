pipeline {
    agent any

    environment {
        REGISTRY = "ozdemirosman"
        REPO = "project4-devops"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/ozdemirosman63/jenkins-ci-cd.git'
            }
        }

        stage('Get Commit SHA') {
            steps {
                script {
                    env.IMAGE_TAG = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    env.DOCKER_IMAGE = "${REGISTRY}/${REPO}:${IMAGE_TAG}"
                }
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE} ."
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                }
            }
        }

        stage('Docker Push') {
            steps {
                sh "docker push ${DOCKER_IMAGE}"
            }
        }

        stage('Generate Deployment YAML') {
            steps {
                sh 'sed "s|IMAGE_TAG|${IMAGE_TAG}|" k8s/deployment-template.yaml > deployment.yaml'
            }
        }

        stage('Deploy to K8s') {
            steps {
                sh "kubectl apply -f deployment.yaml"
                sh "kubectl apply -f service.yaml"
            }
        }
    }
}
