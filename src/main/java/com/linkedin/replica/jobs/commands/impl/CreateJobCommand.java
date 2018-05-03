package com.linkedin.replica.jobs.commands.impl;

import com.google.gson.JsonObject;
import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class CreateJobCommand  extends Command {
    public CreateJobCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() throws IOException, SQLException {
        JobsHandler jobsHandler = (JobsHandler) this.dbHandler;
        validateArgs(new String[]{"userId","jobTitle","companyId"});
        JsonObject request = (JsonObject) args.get("request");
        jobsHandler.createJobAsaCompany(request);
        return null;
    }
}
