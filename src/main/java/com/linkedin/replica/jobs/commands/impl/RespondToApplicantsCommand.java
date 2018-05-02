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

        String userId = ((JsonObject) args.get("userId")).getAsString();
        String jobId = ((JsonObject) args.get("userId")).getAsString();
        String applicantId = ((JsonObject) args.get("userId")).getAsString();
        int response = ((JsonObject) args.get("response")).getAsInt();
        jobsHandler.respondToJobsAsCompany(userId, jobId, applicantId, response);
        return null;
    }
}

