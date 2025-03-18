#!/usr/bin/env groovy
def call(String k8sCredentialsID, String repoName, String imageName, String deploymentFile) {
    // تحميل ملف الـ deployment من Shared Library
    def deployYamlContent = libraryResource(deploymentFile)
    
    // كتابة المحتوى إلى ملف فعلي داخل Workspace
    writeFile file: deploymentFile, text: deployYamlContent

    // تحديث اسم صورة Docker في ملف النشر
    sh "sed -i 's|image:.*|image: ${repoName}/${imageName}:${BUILD_NUMBER}|g' ${deploymentFile}"

    // استخدام kubeconfig المخزن في Jenkins Credentials
    withCredentials([file(credentialsId: "${k8sCredentialsID}", variable: 'KUBECONFIG_FILE')]) {
        // تعيين KUBECONFIG بشكل صحيح
        sh '''
            export KUBECONFIG=${KUBECONFIG_FILE}
            kubectl cluster-info
            kubectl apply -f ${deploymentFile}
        '''
    }
}

