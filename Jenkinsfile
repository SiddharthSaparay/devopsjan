pipeline {
    agent any

    environment {
        // Maven full path (Windows)
        MVN = '"C:\\apache-maven-3.9.12\\bin\\mvn.cmd"'

        // Local image built in Jenkins
        LOCAL_IMAGE    = "mvnproj:1.0"

        // DockerHub push target
        DOCKERHUB_USER  = "siddharthsaparay"
        IMAGE_NAME      = "mymvnproj"
        IMAGE_TAG       = "latest"

        // Container name on Jenkins machine
        CONTAINER_NAME  = "mymvnproj_container"
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo "Pulling from GitHub repository"
                git branch: 'main',
                    credentialsId: 'github',
                    url: 'https://github.com/SiddharthSaparay/devopsjan.git'
            }
        }

        stage('Tool Check') {
            steps {
                bat '%MVN% -version'
                bat 'java -version'
                bat 'docker version'
            }
        }

        stage('Test the Project') {
            steps {
                echo "Running tests"
                bat '%MVN% clean test'
            }
            post {
                always {
                    junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true
                    echo 'Test stage finished!'
                }
            }
        }

        stage('Build Project (Jar)') {
            steps {
                echo "Building JAR"
                bat '%MVN% clean package -DskipTests'
                bat 'dir target\\*.jar'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker image"
                bat "docker build -t %LOCAL_IMAGE% ."
            }
        }

        stage('Push Docker Image to DockerHub') {
            steps {
                echo "Login + Tag + Push"
                withCredentials([usernamePassword(credentialsId: 'docker', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat """
                    echo %DOCKER_PASS% | docker login -u %DOCKERHUB_USER% --password-stdin
                    if %ERRORLEVEL% NEQ 0 exit /b 1

                    docker tag %LOCAL_IMAGE% %DOCKERHUB_USER%/%IMAGE_NAME%:%IMAGE_TAG%
                    docker push %DOCKERHUB_USER%/%IMAGE_NAME%:%IMAGE_TAG%
                    """
                }
            }
        }

        stage('Deploy using Container') {
            steps {
                echo "Running container"
                bat """
                docker rm -f %CONTAINER_NAME% 2>nul || exit /b 0
                docker run -d --name %CONTAINER_NAME% %DOCKERHUB_USER%/%IMAGE_NAME%:%IMAGE_TAG%
                docker ps
                docker logs %CONTAINER_NAME%
                """
            }
        }
    }

    post {
        success { echo 'Pipeline succeeded!' }
        failure { echo 'Pipeline failed!' }
        always  { echo 'Pipeline finished.' }
    }
}

