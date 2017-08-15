import com.company.utils.pipelineFunctions;

def run_in_stage(String stage_name, Closure command){
    def ris = new pipelineFunctions()
    String emailadd = 'doesnt matter'
    ris.run_in_stage(stage_name, command, emailadd)
}

def call(body) {

    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    node {
        // Clean workspace before doing anything
        deleteDir()


        try {
            //run_in_stage('Clone', {
            run_in_stage('Clone', {
                    checkout scm
                })

            stage ('Build') {
                sh "echo 'building ${config.projectName} ...'"
            }
            stage ('Tests') {
                parallel 'static': {
                    sh "echo 'shell scripts to run static tests...'"
                },
                'unit': {
                    sh "echo 'shell scripts to run unit tests...'"
                },
                'integration': {
                    sh "echo 'shell scripts to run integration tests...'"
                }
            }
            stage ('Deploy') {
                sh "echo 'deploying to server ${config.serverDomain}...'"
                sh "echo Itai ganot"
            }
        } catch (err) {
            currentBuild.result = 'FAILED'
            throw err
        }
    }
}
