@Library('SharedLibraryNTI') _ 

pipeline {
    agent { label 'slave' }
    
    environment {
        GITHUB_REPO_URL    = 'https://github.com/IbrahimAdell/App3.git'
        GITHUB_REPO_BRANCH = 'main'
        DOCKER_REGISTRY    = "ranasalem2412"
        DOCKER_IMAGE       = "rana_image2"
        DOCKERHUB_CRED_ID  = "dockerhub"
        K8S_CRED_ID        = 'kube'
        DEPLOYMENT         = 'deployment.yaml' // اسم الملف الذي سيتم تحميله من المكتبة
    }
    
    stages {
        stage('Clone Repository') {
            steps {
                git url: GITHUB_REPO_URL, branch: GITHUB_REPO_BRANCH
            }
        }
        
        stage('Build Application') {
            steps {
                sh 'mvn clean package'
            }
        }
        
        stage('Manage Docker Image') {
            steps {
                script {
                    buildandPushDockerImage("${DOCKERHUB_CRED_ID}", "${DOCKER_REGISTRY}", "${DOCKER_IMAGE}")
                }
            }
        }
        
        stage('Deploy to Kubernetes') {
            steps {
                script {
                    deployOnKubernetes("${K8S_CRED_ID}", "${DOCKER_REGISTRY}", "${DOCKER_IMAGE}", "${DEPLOYMENT}")
                }
            }
        }
    }
    
    post {
        success {
            echo "Deployment successful!"
        }
        failure {
            echo "Build or deployment failed."
        }
    }
}

        
       
