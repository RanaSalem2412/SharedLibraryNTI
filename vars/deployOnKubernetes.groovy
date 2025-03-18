#!/usr/bin/env groovy
def call(String k8sCredentialsID, String repoName, String imageName, String deploymentFile) {
    // تحميل ملف الـ deployment من resources داخل SharedLibraryNTI
    def deployYamlContent = libraryResource(deploymentFile)
    
    // كتابة المحتوى إلى ملف فعلي داخل Workspace
    writeFile file: deploymentFile, text: deployYamlContent

    // تحديث اسم صورة Docker في ملف النشر
    sh "sed -i 's|image:.*|image: ${repoName}/${imageName}:${BUILD_NUMBER}|g' ${deploymentFile}"

    // تنفيذ النشر على Kubernetes باستخدام kubeconfig المخزن في Jenkins Credentials
    withCredentials([file(credentialsId: "${k8sCredentialsID}", variable: 'KUBECONFIG_FILE')]) {
        sh "export KUBECONFIG=${KUBECONFIG_FILE} && kubectl apply -f ${deploymentFile}"
    }
}
