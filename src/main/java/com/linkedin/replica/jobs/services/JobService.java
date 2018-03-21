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

    public Object serve(String commandName, HashMap<String, String> args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        Class<?> commandClass = config.getCommandClass(commandName);
        Constructor constructor = commandClass.getConstructor(HashMap.class);
        Command command = (Command) constructor.newInstance(args);

        Class<?> noSqlHandlerClass = config.getHandlerClass("j");
       JobsHandler noSqlHandler = (JobsHandler) noSqlHandlerClass.newInstance();

        command.setDbHandler(noSqlHandler);

        return command.execute();
    }
}