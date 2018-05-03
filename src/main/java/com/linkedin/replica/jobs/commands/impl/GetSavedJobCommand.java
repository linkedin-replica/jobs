package com.linkedin.replica.jobs.commands.impl;

import com.google.gson.JsonObject;
import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.models.Job;
import com.linkedin.replica.jobs.models.ReturnedJob;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GetSavedJobCommand extends Command {
    public GetSavedJobCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() throws IOException, SQLException {
        validateArgs(new String[]{"userId"});
        JobsHandler jobsHandler = (JobsHandler) this.dbHandler;
        JsonObject request = (JsonObject) args.get("request");
        String userId = (request.get("userId")).getAsString();
        ArrayList<ReturnedJob> jobs =  jobsHandler.getSavedJobs(userId);
        return jobs;
    }
}
