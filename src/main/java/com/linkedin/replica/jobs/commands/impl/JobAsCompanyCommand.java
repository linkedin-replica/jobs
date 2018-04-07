package com.linkedin.replica.jobs.commands.impl;

import com.linkedin.replica.jobs.cache.handlers.JobsCacheHandler;
import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;

import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.models.Job;
import com.linkedin.replica.jobs.models.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class JobAsCompanyCommand extends Command {
    private JobsCacheHandler jobsCacheHandler;
    public JobAsCompanyCommand(HashMap<String, Object> args) {
            super(args);
    }

    /*
     * add job to the database and cache it.
     */
    public Object execute() throws IOException {

        validateArgs(new String[]{"jobId"});
            // get notifications from db
        JobsHandler jobsHandler = (JobsHandler) this.dbHandler;
        String jobId = (String) args.get("jobId");
        Job job = new Job(jobId,(String)args.get("industryType"),(String) args.get("employmentType"),(String) args.get("jobFunctions"),
                (String) args.get("positionName"),(String) args.get("professionLevel"),(String)args.get("companyID"),(String)args.get("companyName"),
                (String)  args.get("companyLocation"),(String)args.get("companyProfilePicture"),(String)args.get("jobBrief"));
        jobsCacheHandler = (JobsCacheHandler) cacheHandler;
        jobsHandler.createJobAsaCompany(job);
        jobsCacheHandler.saveJobListing(new String[] {jobId}, Job.class);
        return "Job Posted";
    }
}



