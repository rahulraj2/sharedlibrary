library 'personal-rahul-sandbox'

pipeline{
    agent none
    stages{
        stage('Calling Build Method'){
            steps{
                script{
                    build()
                    echo "${kuchhbhi}"
                    sh "docker build -t ${ENV_APPLICATION_NAME} . --no-cache"
                    bat label: '', script: "docker build -t '${ENV_APPLICATION_NAME}' . --no-cache"
                }
            }
            

        }
    }
}