@Library('SharedLibraryNTI') _

pipeline {
    agent { label 'slave' } // تأكدي أن الـ label متطابق مع اسم الـ Jenkins Slave الذي أعددته

    environment {
        // متغيرات البيئة اللازمة
        GITHUB_REPO_URL = 'https://github.com/IbrahimAdell/App3.git'
        GITHUB_REPO_BRANCH = 'main'
        DOCKER_REGISTRY = "ranasalem2412"         // اسم حسابك على Docker Hub
        DOCKER_IMAGE = "rana_image2"              // اسم الصورة على Docker Hub
        DOCKERHUB_CRED_ID = "dockerhub"           // الـ ID الذي أنشأته لـ DockerHub credentials في Jenkins
        K8S_CRED_ID = 'kube'                      // الـ ID الذي أنشأته لـ kubeconfig credentials في Jenkins
        DEPLOYMENT = 'deployment.yaml'            // ملف النشر الخاص بـ Kubernetes
    }

    stages {
        stage('Clone Repository') {
            steps {
                // استنساخ الكود من مستودع التطبيق
                git url: GITHUB_REPO_URL, branch: GITHUB_REPO_BRANCH    
            }
        }

        stage('Manage Docker Image') {
            steps {
                script {
                    // استدعاء الدالة الموجودة في مكتبة Jenkins المشتركة لبناء ودفع صورة Docker
                    buildandPushDockerImage("${DOCKERHUB_CRED_ID}", "${DOCKER_REGISTRY}", "${DOCKER_IMAGE}")
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // استدعاء الدالة الموجودة في مكتبة Jenkins المشتركة لنشر التطبيق على Kubernetes
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
