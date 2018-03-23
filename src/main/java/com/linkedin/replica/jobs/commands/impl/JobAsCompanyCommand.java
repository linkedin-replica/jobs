package com.linkedin.replica.jobs.commands.impl;

import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;

import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.models.Job;
import com.linkedin.replica.jobs.models.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class JobAsCompanyCommand extends Command {

        public JobAsCompanyCommand(HashMap<String, Object> args) {
            super(args);
        }

        public Object execute() throws IOException {

            validateArgs(new String[]{});
            // get notifications from db
            JobsHandler jobsHandler = (JobsHandler) this.dbHandler;;
            try {
                jobsHandler.createJobAsaCompany((Job)args.get("job"),(String)args.get("CompanyId"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "Job Posted";
        }


    }


