package Core;

import com.linkedin.replica.jobs.config.Configuration;
import com.linkedin.replica.jobs.database.handlers.impl.ArangoSQLJobsHandler;
import com.linkedin.replica.jobs.database.DatabaseConnection;
//import models.testUser;
import org.junit.*;
//import utils.ConfigReader;
//
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


import static org.junit.Assert.assertEquals;

public class SqlHandlerTest {
    private static ArangoSQLJobsHandler mysqlHandler;

    static Configuration config;

    @BeforeClass
    public static void init() throws IOException, SQLException, ClassNotFoundException {

        Configuration.init("src/main/resources/config/app.config", "src/main/resources/config/arango.config",
                "src/main/resources/config/database.config", "src/main/resources/config/commands.config",
                "src/main/resources/config/controller.config","src/main/resources/config/cache.config");
        DatabaseConnection.init();
        config = Configuration.getInstance();
        mysqlHandler = new ArangoSQLJobsHandler();
        mysqlHandler.connect();
    }


    @Test
    public void getAppliedjobsTest() throws IOException, SQLException {

        ArrayList<String> jobs = mysqlHandler.getAppliedJobsIDs("1");
        assertEquals("Jobs must be of size 1" ,jobs.size() ,1);

    }
    @Test
    public void RespondToJobTest() {

    }

    @AfterClass
    public static void clean() throws IOException, SQLException, ClassNotFoundException {

        DatabaseConnection.getInstance().closeConnections();
    }



}