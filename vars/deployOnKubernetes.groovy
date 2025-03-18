def call(Map args) {
    def k8sCredentialsID = args.k8sCredentialsID
    def deploymentFile = args.deploymentFile

    withCredentials([file(credentialsId: 'kube', variable: 'KUBECONFIG')]) {
        script {
            echo "KUBECONFIG is set securely"
            sh """
                export KUBECONFIG=${KUBECONFIG}
                kubectl cluster-info
                kubectl apply -f ${deploymentFile}
            """
        }
    }
}
