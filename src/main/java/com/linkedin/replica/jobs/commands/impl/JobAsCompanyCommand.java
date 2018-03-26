package com.linkedin.replica.jobs.commands.impl;

import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;

import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.models.Job;
import com.linkedin.replica.jobs.models.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class JobAsCompanyCommand extends Command {

        public JobAsCompanyCommand(HashMap<String, Object> args) {
            super(args);
        }

        public Object execute() throws IOException {

            validateArgs(new String[]{"jobId"});
            // get notifications from db
            JobsHandler jobsHandler = (JobsHandler) this.dbHandler;;

            Job job = new Job((String) args.get("jobId"),(String)args.get("industryType"),(String)(String)args.get("employementType"),(String) args.get("jobFunctions"),
                    (String) args.get("positionName"),(String) args.get("professionLevel"),(String)args.get("companyID"),(String)args.get("companyName"),
                    (String)  args.get("companyLocation"),(String)args.get("companyProfilePicture"),(String)args.get("jobBrief"));
            jobsHandler.createJobAsaCompany(job);
            return "Job Posted";

        }


    }


