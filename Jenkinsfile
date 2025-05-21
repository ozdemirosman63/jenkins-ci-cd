pipeline {
    agent any

    environment {
        IMAGE_NAME = "ozdemirosman/project4-devops"
        IMAGE_TAG = "${BUILD_NUMBER}" // 🔁 Her build'de farklı tag
    }

    stages {
        stage('Clone Repo') {
            steps {
                git branch: 'main', url: 'https://github.com/ozdemirosman63/jenkins-ci-cd.git', credentialsId: 'github-creds'
            }
        }

        stage('Build Jar') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t $IMAGE_NAME:$IMAGE_TAG ."
            }
        }

        stage('Push to DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh """
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push $IMAGE_NAME:$IMAGE_TAG
                    """
                }
            }
        }

        stage('Kubernetes Deploy') {
            steps {
                // ❗ Deployment'ı güncelleyip yeni image'ı set ediyoruz
                sh "kubectl set image deployment/project4-deployment project4-container=$IMAGE_NAME:$IMAGE_TAG"
                sh 'kubectl apply -f service.yaml'
            }
        }
    }
}
