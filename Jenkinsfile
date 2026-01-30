pipeline {
    agent any

    tools {
        jdk 'Java17'
        maven 'Maven'
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo "Pulling from GITHUB repository"
                git branch: 'main',
                    credentialsId: 'mygithub',
                    url: 'https://github.com/SiddharthSaparay/devopsjan.git'
            }
        }

        stage('Test the Project') {
            steps {
                echo "Test my JAVA project"
                bat 'mvn clean test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    echo 'Test Run succeeded!'
                }
            }
        }

        stage('Build Project') {
            steps {
                echo "Building my JAVA project"
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build the Docker Image') {
            steps {
                echo "Build the Docker Image for mvn project"
                bat 'docker build -t mvnproj:1.0 .'
            }
        }

        stage('Push Docker Image to DockerHub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhubtoken',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    bat '''
                        docker logout
                        echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                        docker tag mvnproj:1.0 %DOCKER_USER%/myapp:latest
                        docker push %DOCKER_USER%/myapp:latest
                    '''
                }
            }
        }

        stage('Deploy the project using k8s') {
            steps {
                echo "Running Java Application in k8s"
                bat '''
                    "C:\\Program Files\\Kubernetes\\Minikube\\minikube.exe" delete
                    "C:\\Program Files\\Kubernetes\\Minikube\\minikube.exe" start
                    "C:\\Program Files\\Kubernetes\\Minikube\\minikube.exe" status

                    "C:\\Program Files\\Kubernetes\\Minikube\\minikube.exe" image load siddharthsaparay/myapp:latest
                    kubectl apply -f deployment.yaml
                    timeout /t 20
                    kubectl get pods
                    kubectl apply -f services.yaml
                    timeout /t 10
                    kubectl get services
                    "C:\\Program Files\\Kubernetes\\Minikube\\minikube.exe" image ls
                '''
            }
        }

        stage('Parallel loading of services and Dashboard') {
            parallel {

                stage('Run Minikube Dashboard') {
                    steps {
                        echo "Running minikube dashboard"
                        bat '''
                            "C:\\Program Files\\Kubernetes\\Minikube\\minikube.exe" addons enable metrics-server
                            "C:\\Program Files\\Kubernetes\\Minikube\\minikube.exe" dashboard
                            echo "Dashboard is Running"
                        '''
                    }
                }

                stage('Run Minikube Services') {
                    steps {
                        echo "Running minikube services"
                        bat '''
                            "C:\\Program Files\\Kubernetes\\Minikube\\minikube.exe" service --all
                            echo "All services are Running"
                        '''
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'I succeeded!'
        }
        failure {
            echo 'Failed........'
        }
    }
}