package database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.List;
import org.javalite.activejdbc.Base;
import models.Job;
import config.DatabaseConnections;

public class MysqlHandler implements DatabaseHandler {
    Connection mySqlConnection;

    /**
     *
     */
    private Properties properties;

    /**
     * @throws IOException
     */
    public MysqlHandler() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
        mySqlConnection = DatabaseConnections.getInstance().getMysqlConn();
    }

    public void connect() {
        Base.open(properties.getProperty("development.driver"), properties.getProperty("development.url"), properties.getProperty("development.username"), properties.getProperty("development.password"));
    }


    public Job getJob(String JobID){
        return null;
    }

    @Override
    public List<Job> getSavedJobs(String userId) {
        return null;
    }

    @Override
    public void createJobAsaCompany(Job job) {

    }

    @Override
    public void deleteJobAsaCompany(String jobID) {

    }

    public ArrayList<String> getAppliedJobsIDs(String userId) throws SQLException {
        ArrayList<String>Ids = new ArrayList<>();
        String query = "{CALL view_applied_jobs(?);}";
        CallableStatement stmt = mySqlConnection.prepareCall(query);
        stmt.setString(1, userId);
        ResultSet result = stmt.executeQuery();
        try{
        while (result.next()) {
            String id = result.getString("id");
            Ids.add(id);
            System.out.println(id + "\t" );
        }
    } catch (SQLException e ) {
            System.out.println("Exception ");
    } finally {
        if (stmt != null) { stmt.close(); }
    }
        return Ids;

    }

    public void disconnect() {
        Base.close();
    }

    /**
     * Get the user of an email or null if it's not found
     * @param email of the user
     * @return User
     */


}