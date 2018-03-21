package com.linkedin.replica.jobs.database;

/**
 * A singleton class carrying a database instance
 */

import java.io.IOException;
import java.sql.SQLException;

import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.database.handlers.impl.ArangoSQLJobsHandler;

public class DatabaseInstance {

    // TODO uncouple arango, read from some config file
    private static JobsHandler db;

    static {
        try {
            db = new ArangoSQLJobsHandler();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private DatabaseInstance() {
    }

    /**
     * Get a singleton DB instance
     *
     * @return The DB instance
     */
    public static JobsHandler getInstance() {
        return db;
    }
}