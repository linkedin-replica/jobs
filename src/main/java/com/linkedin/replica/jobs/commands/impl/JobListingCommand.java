package com.linkedin.replica.jobs.commands.impl;

import com.google.gson.JsonObject;
import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.models.Job;
import com.linkedin.replica.jobs.models.ReturnedJob;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class JobListingCommand extends Command {

    public JobListingCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() throws IOException {
        System.out.println("dkmdkmd");
        validateArgs(new String[]{"jobId"});
        JobsHandler jobsHandler = (JobsHandler) this.dbHandler;;
        JsonObject request = (JsonObject) args.get("request");
        String jobId = (request.get("jobId")).getAsString();
        ReturnedJob job =  jobsHandler.getJob(jobId);
        return job;
    }
}
