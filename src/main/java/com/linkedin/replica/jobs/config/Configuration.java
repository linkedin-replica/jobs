package com.linkedin.replica.jobs.config;

import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Configuration {
    private final Properties commandConfig = new Properties();
    private final Properties appConfig = new Properties();
    private final Properties arangoConfig = new Properties();
    private final Properties controllerConfig = new Properties();

    private String appConfigPath;
    private String databaseConfigPath;
    private String commandsConfigPath;
    private String arangoConfigPath;
    private static Configuration instance;

    private boolean isAppConfigModified;
    private boolean isArangoConfigModified;
    private boolean isCommandsConfigModified;

    private Configuration(String databaseConfigPath, String commandsConfigPath,
                          String arangoConfigPath) {
        this.databaseConfigPath = databaseConfigPath;
        this.commandsConfigPath = commandsConfigPath;
        this.arangoConfigPath = arangoConfigPath;
    }


    public static Configuration getInstance(String databaseConfigPath, String commandConfigPath,
                                            String arangoNamesConfigPath) {

        if(instance == null){
            synchronized (Configuration.class) {
                if(instance == null){
                    instance = new Configuration(databaseConfigPath, commandConfigPath, arangoNamesConfigPath);
                }
            }
        }
        return instance;
    }

    public static Configuration getInstance(){
        return instance;
    }

    public String getDatabaseConfigPath() {
        return databaseConfigPath;
    }

    public String getCommandsConfigPath() {
        return commandsConfigPath;
    }

    public String getControllerConfigProp(String key){
        return controllerConfig.getProperty(key);
    }

    public String getAppConfigProp(String key) {
        return appConfig.getProperty(key);
    }

    public void setAppControllerProp(String key, String val){
        if(val != null)
            appConfig.setProperty(key, val);
        else
            appConfig.remove(key); // remove property if val is null

        isAppConfigModified = true;
    }

    public void setCommandsConfigProp(String key, String val){
        if(val != null)
            commandConfig.setProperty(key, val);
        else
            commandConfig.remove(key); // remove property if val is null

        isCommandsConfigModified = true;
    }

    /**
     * Commit changes to write modifications in configuration files
     * @throws IOException
     */
    public void commit() throws IOException {
        if(isAppConfigModified){
            writeConfig(appConfigPath, appConfig);
            isAppConfigModified = false;
        }

        if(isArangoConfigModified){
            writeConfig(arangoConfigPath, arangoConfig);
            isArangoConfigModified = false;
        }

        if(isCommandsConfigModified){
            writeConfig(commandsConfigPath, commandConfig);
            isCommandsConfigModified = false;
        }
    }

    private void writeConfig(String filePath, Properties properties) throws IOException{
        // delete configuration file and then re-write it
        Files.deleteIfExists(Paths.get(filePath));
        OutputStream out = new FileOutputStream(filePath);
        properties.store(out, "");
        out.close();
    }

    public String getArangoConfigPath() {
        return arangoConfigPath;
    }
    public String getAppConfig(String key) {
        return appConfig.getProperty(key);
    }
    public String getArangoConfig(String key) {
        return arangoConfig.getProperty(key);
    }

    public Class getCommandClass(String commandName) throws ClassNotFoundException {
        String commandsPackageName = Command.class.getPackage().getName() + ".impl";
        String commandClassPath = commandsPackageName + '.' + commandConfig.get(commandName);
        return Class.forName(commandClassPath);
    }

    public Class getHandlerClass(String commandName) throws ClassNotFoundException {
        String handlerPackageName = DatabaseHandler.class.getPackage().getName() + ".impl";
        String handlerClassPath = handlerPackageName + "." + commandConfig.get(commandName + ".handler");
        return Class.forName(handlerClassPath);
    }

}