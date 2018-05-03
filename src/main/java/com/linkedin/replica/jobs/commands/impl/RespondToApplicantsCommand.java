package com.linkedin.replica.jobs.commands.impl;

import com.google.gson.JsonObject;
import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RespondToApplicantsCommand extends Command {
    public RespondToApplicantsCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() throws SQLException {
        validateArgs(new String[]{"userId", "jobId", "applicantId", "response"});
        JobsHandler jobsHandler = (JobsHandler) this.dbHandler;
        JsonObject request = (JsonObject) args.get("request");
        String userId = (request.get("userId")).getAsString();
        String jobId = (request.get("jobId")).getAsString();
        String applicantId = (request.get("applicantId")).getAsString();
        int response = (request.get("response")).getAsInt();
        jobsHandler.respondToJobsAsCompany(userId, jobId, applicantId, response);
        return null;
    }
}

