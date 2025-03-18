def deployOnKubernetes(k8sCredentialsID, deploymentFile) {
    withCredentials([file(credentialsId: k8sCredentialsID, variable: 'KUBECONFIG')]) {
        sh '''
            echo "KUBECONFIG is set to: ${KUBECONFIG}"
            export KUBECONFIG=${KUBECONFIG}
            kubectl cluster-info
            kubectl apply -f ${deploymentFile}
        '''
    }
}
