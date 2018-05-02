package com.linkedin.replica.jobs.commands.impl;

import com.google.gson.JsonObject;
import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;
import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DeleteJobAsCompanyCommand extends Command {

    public DeleteJobAsCompanyCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() throws IOException, SQLException {
        JobsHandler jobsHandler = (JobsHandler) this.dbHandler;
        validateArgs(new String[]{"userId","jobId"});
        String userId = ((JsonObject) args.get("userId")).getAsString();
        String jobId = ((JsonObject) args.get("jobId")).getAsString();
        jobsHandler.deleteJobAsaCompany(userId, jobId);
        return null;
    }


}


