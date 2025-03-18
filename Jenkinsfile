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
        // نستخدم هذا المتغير كاسم افتراضي؛ ولكن سيتم تحميل الملف من المكتبة
        DEPLOYMENT         = 'deployment.yaml'
    }
    
    stages {
        stage('Clone Repository') {
            steps {
                // استنساخ الكود من مستودع التطبيق
                git url: GITHUB_REPO_URL, branch: GITHUB_REPO_BRANCH
            }
        }
        
        stage('Build Application') {
            steps {
                // بناء التطبيق باستخدام Maven لإنشاء ملف JAR في مجلد target
                sh 'mvn clean package'
            }
        }
        
        stage('Manage Docker Image') {
            steps {
                script {
                    // استدعاء الدالة الموجودة في المكتبة المشتركة لبناء ودفع صورة Docker
                    buildandPushDockerImage("${DOCKERHUB_CRED_ID}", "${DOCKER_REGISTRY}", "${DOCKER_IMAGE}")
                }
            }
        }
        
        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // تحميل ملف deployment.yaml من مكتبة SharedLibraryNTI (يجب أن يكون داخل resources/)
                    def deployYamlContent = libraryResource 'deployment.yaml'
                    // كتابة الملف إلى مساحة العمل
                    writeFile file: 'deployment.yaml', text: deployYamlContent
                    
                    // تعديل الملف لتحديث صورة الـ Docker (يمكنك تعديل هذا السطر حسب الحاجة)
                    sh "sed -i 's|image:.*|image: ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:latest|g' deployment.yaml"
                    
                    // تطبيق ملف النشر على Kubernetes
                    sh "kubectl apply -f deployment.yaml"
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

        
       
