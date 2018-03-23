package com.linkedin.replica.jobs.commands;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;

public abstract class Command {
    protected HashMap<String, String> args;
    protected JobsHandler jobsHandler;
    public Command(HashMap<String, String> args) {
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
    public abstract LinkedHashMap<String, Object> execute() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;
    public void setDbHandler(JobsHandler dbHandler) {
        this.jobsHandler = dbHandler;
    }
    protected void validateArgs(String[] requiredArgs) {
        for(String arg: requiredArgs)
            if(!args.containsKey(arg)) {
                String exceptionMsg = String.format("Cannot execute command. %s argument is missing", arg);
                throw new IllegalArgumentException(exceptionMsg);
            }
    }
    public void setArgs(HashMap<String, String> args) {
        this.args = args;
    }

}