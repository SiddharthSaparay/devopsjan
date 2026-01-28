stage('Push Docker Image to DockerHub') {
    steps {
        echo "Push Docker Image to DockerHub for mvn project"

        withCredentials([usernamePassword(
            credentialsId: 'dockerhubpwd',
            usernameVariable: 'DOCKER_USER',
            passwordVariable: 'DOCKER_PASS'
        )]) {

            bat '''
            echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
            docker tag mvnproj:1.0 siddharthsaparay/mymvnproj:latest
            docker push siddharthsaparay/mymvnproj:latest
            '''
        }
    }
}
