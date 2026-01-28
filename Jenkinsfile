pipeline {
    agent any
    tools{
        jdk'Java17'
        maven'Maven'
    }
    
    stages {
        stage('Checkout Code') {
            steps {
               echo "Pulling from GITHUB repository"
               git branch: 'main', credentialsId: 'mygithub', url: 'https://github.com/SiddharthSaparay/devopsjan.git'
            }
        }
        stage('Build Project') {
            steps {
               echo "Building my JAVA project"
               bat 'mvn clean package'
            }
        }
        stage('Test The Appln') {
            steps {
               echo "Testing my JAVA project"
            }
        }
        stage('Deploy the project') {
            steps {
                echo "Project is getting Deployed"
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
