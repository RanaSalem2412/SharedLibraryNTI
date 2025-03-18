@Library('SharedLibraryNTI') _  // استدعاء المكتبة المشتركة

pipeline {
    agent { label 'slave' }
    
    environment {
        GITHUB_REPO_URL    = 'https://github.com/IbrahimAdell/App3.git'
        GITHUB_REPO_BRANCH = 'main'
        DOCKER_REGISTRY    = "ranasalem2412"
        DOCKER_IMAGE       = "rana_image2"
        DOCKERHUB_CRED_ID  = "dockerhub"
        K8S_CRED_ID        = 'kube'  // تعريف معرف الكريديشينال للـ K8S
        DEPLOYMENT         = 'deployment.yaml' // اسم الملف الذي سيتم نشره
    }
    
    stages {
        stage('Clone Repository') {
            steps {
                // استنساخ المستودع من GitHub
                git url: GITHUB_REPO_URL, branch: GITHUB_REPO_BRANCH
            }
        }
        
        stage('Build Application') {
            steps {
                // بناء التطبيق باستخدام Maven
                sh 'mvn clean package'
            }
        }
        
        stage('Manage Docker Image') {
            steps {
                script {
                    // استدعاء الدالة من المكتبة المشتركة لبناء ودفع الصورة
                    buildandPushDockerImage("${DOCKERHUB_CRED_ID}", "${DOCKER_REGISTRY}", "${DOCKER_IMAGE}")
                }
            }
        }
        
        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // استدعاء الدالة من المكتبة المشتركة لنشر التطبيق على Kubernetes
                    deployOnKubernetes(k8sCredentialsID: "${K8S_CRED_ID}", deploymentFile: "${DEPLOYMENT}")
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

        
       
