package com.linkedin.replica.jobs.commands.impl;

import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RespondToApplicantsCommand extends Command {
    public RespondToApplicantsCommand(HashMap<String, String> args) {
        super(args);
    }

    public Object execute(){
        validateArgs(new String[]{"userId", "jobId"});
        // get notifications from db
        JobsHandler jobsHandler = (JobsHandler) this.dbHandler;;

        boolean job = false;
        try {
            job = jobsHandler.RespondToJobsAsCompany(args.get("userId"), args.get("jobId"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "The respond was delivered";
    }
}

