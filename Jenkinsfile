pipeline {
    agent any

    environment {
        IMAGE_NAME = "ozdemirosman/project4-devops"
        IMAGE_TAG = "${BUILD_NUMBER}" // Her build'de benzersiz tag
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
                sh """
                    docker build -t $IMAGE_NAME:$IMAGE_TAG .
                    docker tag $IMAGE_NAME:$IMAGE_TAG $IMAGE_NAME:latest
                """
            }
        }

        stage('Push to DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh """
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker push $IMAGE_NAME:$IMAGE_TAG
                        docker push $IMAGE_NAME:latest
                    """
                }
            }
        }

        stage('Kubernetes Deploy') {
            steps {
                sh "kubectl set image deployment/project4-deployment project4-container=$IMAGE_NAME:$IMAGE_TAG"
                sh 'kubectl apply -f service.yaml'
            }
        }
    }
}
