package com.linkedin.replica.jobs.commands.impl;

import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.models.Job;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class JobListingCommand extends Command {

    public JobListingCommand(HashMap<String, String> args) {
        super(args);
    }

    public Object execute() throws IOException {
        validateArgs(new String[]{"jobId"});
        // get notifications from db
        JobsHandler jobsHandler = (JobsHandler) this.dbHandler;;
        System.out.println(dbHandler);
        Job job =  jobsHandler.getJob(args.get("jobId"));
        return job;
    }
}
