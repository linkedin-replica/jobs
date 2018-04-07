package com.linkedin.replica.jobs.commands.impl;

import com.linkedin.replica.jobs.cache.handlers.CacheHandler;
import com.linkedin.replica.jobs.cache.handlers.JobsCacheHandler;
import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.models.Job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JobListingCommand extends Command {

    private JobsCacheHandler jobsCacheHandler;
    public JobListingCommand(HashMap<String, Object> args) {
        super(args);
    }

    /*
     * Get jobListing from database if it is not cache, otherwise get from database and cache it.
     */
    public Object execute() throws IOException {
        validateArgs(new String[]{"jobId"});
        // get notifications from db
        JobsHandler jobsHandler = (JobsHandler) this.dbHandler;
        jobsCacheHandler = (JobsCacheHandler) cacheHandler;
        String jobId = (String)args.get("jobId");
        // TODO: check reflection issues.
        Object job = jobsCacheHandler.getJobListingFromCache(jobId, Job.class);
        if(job == null) {
            job = jobsHandler.getJob(jobId);
            jobsCacheHandler.saveJobListing(new String[] {jobId}, new ArrayList<Object>().add(job));
        }
        return job;
    }
}
