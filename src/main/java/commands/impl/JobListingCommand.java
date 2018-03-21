package commands.impl;

import commands.Command;
import database.DatabaseHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class JobListingCommand extends Command {

    public JobListingCommand(HashMap<String, String> args) {
        super(args);
    }

    public LinkedHashMap<String, Object> execute() throws IOException {
        validateArgs(new String[]{"userId"});
        // get notifications from db
        DatabaseHandler dbHandler = (DatabaseHandler) this.dbHandler;

        // ArrayList<Job> Jobs =  dbHandler.createJobAsaCompany(args);
        LinkedHashMap<String, Object> resutls = new LinkedHashMap<String, Object>();
        //resutls.put("response",Jobs);
        return resutls;
    }
}
