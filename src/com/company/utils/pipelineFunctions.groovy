package com.company.utils;

def run_in_stage(String stage_name, Closure command, String sendTo){

    stage (stage_name) {
        try {
            command()
            if (currentBuild.result == 'FAILURE') {
                error "Build failed, see log for further details."
            }
        } catch (Exception ex) {
            echo "Pipeline failed at stage: ${stage_name}"
            throw ex
        }
    }
}

return this;
