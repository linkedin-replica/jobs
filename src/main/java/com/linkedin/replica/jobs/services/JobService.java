package com.linkedin.replica.jobs.services;

import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;
import com.linkedin.replica.jobs.config.Configuration;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class JobService {
    private Configuration config;

    public JobService() throws IOException {
        config = Configuration.getInstance();
    }

    public Object serve(String commandName, HashMap<String, Object> args) throws Exception {
        Class<?> commandClass = config.getCommandClass(commandName);
        Constructor constructor = commandClass.getConstructor(HashMap.class);
        Command command = (Command) constructor.newInstance(args);

        Class<?> dbHandlerClass = config.getDatabaseHandlerClass(commandName);
        command.addDatabaseHandler((DatabaseHandler) dbHandlerClass.newInstance());

        return command.execute();
    }
}