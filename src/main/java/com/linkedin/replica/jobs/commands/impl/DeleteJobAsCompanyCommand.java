package com.linkedin.replica.jobs.commands.impl;

import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;
import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DeleteJobAsCompanyCommand extends Command {

    public DeleteJobAsCompanyCommand(HashMap<String, String> args) {
        super(args);
    }

    public LinkedHashMap<String, Object> execute() throws IOException {
       JobsHandler dbHandler = (JobsHandler) this.jobsHandler;
        validateArgs(new String[]{"userId"});
        jobsHandler.deleteJobAsaCompany(args.get("jobId"));
        LinkedHashMap<String, Object>resutls = new LinkedHashMap<String, Object>();
        resutls.put("response",true);
        return resutls;
    }


}


