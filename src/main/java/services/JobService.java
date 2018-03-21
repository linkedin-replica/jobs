package services;

import database.DatabaseHandler;
import commands.Command;
import utils.ConfigReader;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class JobService {
    private ConfigReader config;

    public JobService() throws IOException {
        config = ConfigReader.getInstance();
    }

    public LinkedHashMap<String, Object> serve(String commandName, HashMap<String, String> args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        Class<?> commandClass = config.getCommandClass(commandName);
        Constructor constructor = commandClass.getConstructor(HashMap.class);
        Command command = (Command) constructor.newInstance(args);
        Class<?> noSqlHandlerClass = config.getNoSqlHandler();
        DatabaseHandler noSqlHandler = (DatabaseHandler) noSqlHandlerClass.newInstance();
        command.setDbHandler(noSqlHandler);
        return command.execute();
    }
}