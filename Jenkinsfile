library 'personal-rahul-sandbox'

pipeline{
    agent none
    stages{
        stage('Calling Build Method'){
            steps{
                script{
                    build()
                    sh "docker build -t ${ENV_APPLICATION_NAME} . --no-cache"
                }
            }
            

        }
    }
}