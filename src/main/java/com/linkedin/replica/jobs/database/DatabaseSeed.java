package com.linkedin.replica.jobs.database;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;
import com.linkedin.replica.jobs.config.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class DatabaseSeed {

    private ArangoDB arangoDriver;
    private DatabaseHandler dbHandler;
    private static Configuration config;

    public DatabaseSeed() throws FileNotFoundException, IOException{
        config = Configuration.getInstance();
    }


    /**
     * Method to get json data from json file.
     * @param filePath path to the json file containing the json data
     * @return Iterator of json data
     * @throws IOException
     */
    public static JSONArray getJSONData(String filePath) throws IOException, ParseException, ParseException {
        JSONParser parser = new JSONParser();
        return (JSONArray) parser.parse(new Scanner(new File(filePath)).useDelimiter("\\Z").next());
    }

    /**
     * feed the database with users specified in json file users.json
     * @throws IOException
     * @throws ParseException
     */
    public static void insertUsers() throws IOException, ParseException, SQLException, ClassNotFoundException {
        ArangoDB arangoDB = DatabaseConnection.getInstance().getArangoDriver();
        String dbName = config.getArangoConfigProp("db.name");
        String collectionName = config.getArangoConfigProp("collection.users.name");

        try {
            arangoDB.db(dbName).createCollection(collectionName);
        } catch(ArangoDBException exception) {
            if(exception.getErrorNum() == 1228) {
                arangoDB.createDatabase(dbName);
                arangoDB.db(dbName).createCollection(collectionName);
            } else if(exception.getErrorNum() == 1207){
                //NoOP
            } else {
                throw exception;
            }
        }

        int id = 0;
        BaseDocument userDocument;
        JSONArray users = getJSONData("src/main/resources/data/users.json");
        for (Object user : users) {
            JSONObject userObject = (JSONObject) user;
            userDocument = new BaseDocument();
            userDocument.addAttribute("userId", userObject.get("userId"));
            userDocument.addAttribute("firstName", userObject.get("firstName"));
            userDocument.addAttribute("lastName", userObject.get("lastName"));
            userDocument.addAttribute("headline", userObject.get("headline"));
            userDocument.addAttribute("industry", userObject.get("industry"));
            userDocument.addAttribute("skills", userObject.get("skills"));
            arangoDB.db(dbName).collection(collectionName).insertDocument(userDocument);
            System.out.println("New user document insert with key = " + userDocument.getId());
        }
    }

    /**
     * Insert commands specified in commands.json file to the database collection commands
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException
     */
    public static void insertJobs() throws IOException, ClassNotFoundException, SQLException, ParseException {

        ArangoDB arangoDB = DatabaseConnection.getInstance().getArangoDriver();
        String dbName = config.getArangoConfigProp("db.name");
        String collectionName = config.getArangoConfigProp("collection.jobs.name");

        try{

            System.out.println(dbName+" hereee");
            arangoDB.db(dbName).createCollection(collectionName);

        }catch(ArangoDBException exception){
            //database not found exception
            if(exception.getErrorNum() == 1228){
                arangoDB.createDatabase(dbName);
                arangoDB.db(dbName).createCollection(collectionName);
            } else if(exception.getErrorNum() == 1207) { // duplicate name error
                // NoOP
            }else {
                throw exception;
            }
        }
        int id = 20;
        BaseDocument jobDocument;
        JSONArray jobs = getJSONData("src/main/resources/data/jobs.json");
        for (Object job : jobs) {
            JSONObject jobObject = (JSONObject) job;
            jobDocument = new BaseDocument();
            jobDocument.setKey(id+"");
            jobDocument.addAttribute("JobID", id);
            jobDocument.addAttribute("positionName", jobObject.get("positionName"));
            jobDocument.addAttribute("companyName", jobObject.get("companyName"));
            jobDocument.addAttribute("companyId", jobObject.get("companyId"));
            jobDocument.addAttribute("companyLocation", jobObject.get("companyLocation"));
            jobDocument.addAttribute("professionLevel", jobObject.get("professionLevel"));
            jobDocument.addAttribute("industryType", jobObject.get("industryType"));
            jobDocument.addAttribute("requiredSkills", jobObject.get("requiredSkills"));
            arangoDB.db(dbName).collection(collectionName).insertDocument(jobDocument);
            System.out.println("New job document insert with key = " + jobDocument.getId());
            id++;
        }
    }

    public static void insertCompanies() throws IOException, ClassNotFoundException, SQLException, ParseException {

        ArangoDB arangoDB = DatabaseConnection.getInstance().getArangoDriver();
        String dbName = config.getArangoConfigProp("db.name");
        String collectionName = config.getArangoConfigProp("collection.companies.name");
        try{

            System.out.println(dbName+" hereee");
            arangoDB.db(dbName).createCollection(collectionName);

        }catch(ArangoDBException exception){
            //database not found exception
            if(exception.getErrorNum() == 1228){
                arangoDB.createDatabase(dbName);
                arangoDB.db(dbName).createCollection(collectionName);
            } else if(exception.getErrorNum() == 1207) { // duplicate name error
                // NoOP
            }else {
                throw exception;
            }
        }
    }
    /**
     * Delete commands collection from the database if it exists
     * @throws ArangoDBException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public static void deleteAllJobs() throws ArangoDBException, IOException,SQLException{
        ArangoDB arangoDB = DatabaseConnection.getInstance().getArangoDriver();
        String dbName = config.getArangoConfigProp("db.name");
        String collectionName = config.getArangoConfigProp("collection.jobs.name");
        try {
            DatabaseConnection.getInstance().getArangoDriver().db(dbName).collection(collectionName).drop();
        } catch(ArangoDBException exception) {
            if(exception.getErrorNum() == 1228) {
                System.out.println("Database not found");
            }
        }
        System.out.println("Jobs collection is dropped");
    }

    public static void deleteAllCompanies() throws ArangoDBException, IOException,SQLException{
        ArangoDB arangoDB = DatabaseConnection.getInstance().getArangoDriver();
        String dbName = config.getArangoConfigProp("db.name");
        String collectionName = config.getArangoConfigProp("collection.companies.name");
        try {
            DatabaseConnection.getInstance().getArangoDriver().db(dbName).collection(collectionName).drop();
        } catch(ArangoDBException exception) {
            if(exception.getErrorNum() == 1228) {
                System.out.println("Database not found");
            }
        }
        System.out.println("Jobs collection is dropped");
    }
    /**
     * Drop specified database from Arango Driver
     * @param dbName Database name to be dropped
     * @throws IOException
     */
    public static void dropDatabase(String dbName) throws IOException, SQLException, ClassNotFoundException {
        try {
            DatabaseConnection.getInstance().getArangoDriver().db(dbName).drop();
        } catch(ArangoDBException exception) {
            if(exception.getErrorNum() == 1228) {
                System.out.println("Database not found");
            } else
                throw exception;
        }
    }

    /**
     * Closing connection to the database.
     * @throws ArangoDBException
     * @throws IOException
     */
    public static void closeDBConnection() throws ArangoDBException, IOException, SQLException, ClassNotFoundException {

        DatabaseConnection.getInstance().getArangoDriver().shutdown();
    }
}