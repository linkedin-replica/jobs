package jobs;

import database.ArangoHandler;
import database.DatabaseHandler;
import models.Command;
import models.Job;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import database.ArangoHandler;
import database.DatabaseHandler;
import  models.Command;
import models.Job;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DeleteJobAsCompanyCommand extends Command {

    public DeleteJobAsCompanyCommand(HashMap<String, String> args) {
        super(args);
    }

    public LinkedHashMap<String, Object> execute() throws IOException {
        validateArgs(new String[]{"userId"});
        // get notifications from db
        DatabaseHandler noSqlHandler = (DatabaseHandler) new ArangoHandler();

        this.setDbHandler(noSqlHandler);
        dbHandler.deleteJobAsaCompany(args.get("jobId"));
        LinkedHashMap<String, Object>resutls = new LinkedHashMap<String, Object>();
        resutls.put("response",true);
        return resutls;
    }


}


