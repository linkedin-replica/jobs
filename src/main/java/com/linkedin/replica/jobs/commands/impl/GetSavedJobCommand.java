package com.linkedin.replica.jobs.commands.impl;

import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.models.Job;

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
        JobsHandler jobsHandler = (JobsHandler) this.dbHandler;;
        ArrayList<Job> jobs =  jobsHandler.getSavedJobs((String) args.get("userId"));
        return jobs;
    }
}
