package com.company.utils;
import com.company.utils.Git;

def run_in_stage(String stage_name, Closure command, String sendTo){

    def gitTool = new Git()

    String ulink = gitTool.getCommitter()
    String jlink = "(<${env.BUILD_URL}|Open>)"

    println "============================================================"
    stage (stage_name) {
        try {
            command()
            if (currentBuild.result == 'FAILURE') {
                error "Build failed, see log for further details."
            }
            println "============================================================"
        } catch (Exception ex) {
            def except = "${ex}"
            String emailadd = ulink+'@company.com'
            if (currentBuild.result == null) {
                currentBuild.result = "FAILURE" }
            this.notifyStatus(stage_name, currentBuild.result, except)
            echo "Pipeline failed at stage: ${stage_name}"
            throw ex
        }
    }
}

return this;
