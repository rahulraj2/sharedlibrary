pipeline{
    agent any
    parameters{
        choice(name: 'JOB_TYPE', choices: ['NonProd', 'Production', 'Three'], description: 'Pick Job type')
        string(name: 'DEVELOPER_GIT_URL', defaultValue: '', description: 'provide git URL')
        string(name: 'SERVICE_NAME', defaultValue: '', description: 'Enter the Service Name')
    }
    environment{
        WEBHOOK_TOKEN = credentials("webhooktoken")
    }
    stages{
        stage("Invoking Job DSL"){
            steps{
                script{
                    if(JOB_TYPE == "Production"){
                        BRANCH_INCLUDES = "master"
                    }else{
                        BRANCH_INCLUDES = "Developemnt-*|Release-*"
                    }
                    jobDsl scriptText: """folder("${JOB_TYPE}"){
                        description(\'Folder containing all jobs for ${JOB_TYPE} Jobs\')
                    }
                    multibranchPipelineJob("${JOB_TYPE}/${SERVICE_NAME}") {
                        branchSources {
                            git {
                                id('${BUILD_NUMBER}') // IMPORTANT: use a constant and unique identifier
                                remote("${DEVELOPER_GIT_URL}")
                                credentialsId('gitcred')
                                includes('${BRANCH_INCLUDES}')
                            }
                        }
                        orphanedItemStrategy {
                            discardOldItems {
                               numToKeep(5)
                            }
                        }
                        configure { node ->
                            def webhookTrigger = node / triggers / 'com.igalg.jenkins.plugins.mswt.trigger.ComputedFolderWebHookTrigger' {
                                spec('')
                                token("${WEBHOOK_TOKEN}")
                            }
                        }
                    }"""
                }
            }
        }
    }
}