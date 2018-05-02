package com.linkedin.replica.jobs.commands;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.google.gson.JsonObject;
import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.exceptions.BadRequestException;

public abstract class Command {
    protected HashMap<String, Object> args;
    protected JobsHandler jobsHandler;
    public Command(HashMap<String, Object> args) {
        this.args = args;
    }
    protected DatabaseHandler dbHandler;

    /**
     * Execute the command
     * @return The output (if any) of the command
     */
    public void addDatabaseHandler(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }
    public abstract Object execute() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException;

    protected void validateArgs(String[] requiredArgs) {
        JsonObject object = (JsonObject) args.get("request");
        for(String arg: requiredArgs)
            if(!object.keySet().contains(arg)) {
                String exceptionMsg = String.format("Cannot execute command. %s argument is missing", arg);
                throw new BadRequestException(exceptionMsg);
            }
    }

}
