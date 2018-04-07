package com.linkedin.replica.jobs.commands.impl;

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

    /*
     * Respond to job and return true or false as the result of query.
     */
    public Object execute(){
        validateArgs(new String[]{"userId", "jobId"});
        // get notifications from db
        JobsHandler jobsHandler = (JobsHandler) this.dbHandler;;

        boolean job = false;
        try {
            job = jobsHandler.RespondToJobsAsCompany((String)args.get("userId"), (String)args.get("jobId"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return job;
    }
}

