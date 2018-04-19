package com.linkedin.replica.jobs.commands.impl;

import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.models.Job;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class UserApplyForJobCommand extends Command {

    public UserApplyForJobCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() throws IOException, SQLException {
        validateArgs(new String[]{"userId","jobId"});
        // get notifications from db
        JobsHandler jobsHandler = (JobsHandler) this.dbHandler;
        boolean applied = jobsHandler.userApplyForJob((String)args.get("userId"), (String)args.get("jobId"));
        return  null;
    }
}
