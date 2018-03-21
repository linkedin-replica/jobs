package services;

import config.Configuration;
import database.DatabaseHandler;
import commands.Command;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class JobService {
    private Configuration config;

    public JobService() throws IOException {
        config = Configuration.getInstance();
    }

    public Object serve(String commandName, HashMap<String, String> args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        Class<?> commandClass = config.getCommandClass(commandName);
        Constructor constructor = commandClass.getConstructor(HashMap.class);
        Command command = (Command) constructor.newInstance(args);

        Class<?> dbHandlerClass = config.getHandlerClass(commandName);
        DatabaseHandler dbHandler = (DatabaseHandler) dbHandlerClass.newInstance();

        command.setDbHandler(dbHandler);

        return command.execute();
    }
}