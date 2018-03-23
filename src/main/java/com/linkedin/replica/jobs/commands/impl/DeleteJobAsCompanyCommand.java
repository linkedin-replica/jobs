package com.linkedin.replica.jobs.commands.impl;

import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;
import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DeleteJobAsCompanyCommand extends Command {

    public DeleteJobAsCompanyCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() throws IOException {
       JobsHandler jobsHandler = (JobsHandler) this.dbHandler;;
        validateArgs(new String[]{"jobId"});
        jobsHandler.deleteJobAsaCompany((String) args.get("jobId"));
        return "Job Deleted";
    }


}


