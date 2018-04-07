package com.linkedin.replica.jobs.commands.impl;

import com.linkedin.replica.jobs.cache.handlers.JobsCacheHandler;
import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;
import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DeleteJobAsCompanyCommand extends Command {

    private JobsCacheHandler jobsCacheHandler;
    public DeleteJobAsCompanyCommand(HashMap<String, Object> args) {
        super(args);
    }

    /*
     * delete job from database and delete it from cache if exists.
     */
    public Object execute() throws IOException {
       JobsHandler jobsHandler = (JobsHandler) this.dbHandler;;
        validateArgs(new String[]{"jobId"});
        String jobId = (String) args.get("jobId");
        jobsCacheHandler = (JobsCacheHandler) cacheHandler;
        jobsHandler.deleteJobAsaCompany(jobId);
        jobsCacheHandler.deleteJobListing(jobId);
        return "Job Deleted";
    }


}


