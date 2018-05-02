package com.linkedin.replica.jobs.commands.impl;

import com.google.gson.JsonObject;
import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class SaveJobCommand  extends Command {
    public SaveJobCommand(HashMap<String, Object> args) {
        super(args);
    }
    public Object execute() throws IOException, SQLException {
        JobsHandler jobsHandler = (JobsHandler) this.dbHandler;
        validateArgs(new String[]{"userId","jobId"});
        String userId = ((JsonObject) args.get("userId")).getAsString();
        String jobId = ((JsonObject) args.get("jobId")).getAsString();
        jobsHandler.userSaveJob(userId, jobId);
        return null;
    }
}
