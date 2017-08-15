package com.company.utils;

def run_in_stage(String stage_name, Closure command, String sendTo){

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
            echo "Pipeline failed at stage: ${stage_name}"
            throw ex
        }
    }
}

return this;
