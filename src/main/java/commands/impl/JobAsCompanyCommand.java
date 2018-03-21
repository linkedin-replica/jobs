package commands.impl;

import commands.Command;
import database.DatabaseHandler;

import models.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class JobAsCompanyCommand extends Command {

        public JobAsCompanyCommand(HashMap<String, String> args) {
            super(args);
        }

        public LinkedHashMap<String, Object> execute() throws IOException {

            validateArgs(new String[]{"userId"});
            // get notifications from db
            DatabaseHandler dbHandler = (DatabaseHandler) this.dbHandler;

            Job job = new Job(args.get("jobId"),args.get("industryType"),args.get("employementType"), args.get("jobFunctions"),
                args.get("positionName"), args.get("professionLevel"),args.get("companyID"),args.get("companyName"),
                            args.get("companyLocation"),args.get("companyProfilePicture"),args.get("jobBrief"));
            dbHandler.createJobAsaCompany(job);
            LinkedHashMap<String, Object>resutls = new LinkedHashMap<String, Object>();
            resutls.put("response",true);
            return resutls;

        }


    }


