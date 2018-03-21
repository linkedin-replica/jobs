import com.arangodb.ArangoDatabase;
import database.ArangoHandler;
import database.DatabaseConnection;
//import models.testUser;
import database.MysqlHandler;
import models.Job;
import org.junit.*;
import utils.ConfigReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;


import static org.junit.Assert.assertEquals;

public class SqlHandlerTest {
    private static MysqlHandler mysqlHandler;

    static ConfigReader config;

    @BeforeClass
    public static void init() throws IOException, SQLException, ClassNotFoundException {
        ConfigReader.isTesting = true;
        config = ConfigReader.getInstance();
        mysqlHandler = new MysqlHandler();

    }


    @Test
    public void getAppliedjobsTest() throws IOException, SQLException {
        ArrayList<String> ids = new ArrayList<String>();
        ids.add("144616");
        ids.add("156540");
        ArrayList<String> jobs = mysqlHandler.getAppliedJobsIDs("31");
        assertEquals("Jobs must be of size 2" ,jobs.size() ,2);

    }


    @AfterClass
    public static void clean() throws IOException, SQLException, ClassNotFoundException {
        ConfigReader.isTesting = false;
        DatabaseConnection.getDBConnection().closeConnections();
    }

//    @AfterClass
//    public static void cleanAfterTest() throws IOException {
//        arangoDb.collection(
//                config.getArangoConfig("collection.users.name")
//        ).drop();
//    }
//
//    @AfterClass
//    public static void clean() throws IOException {
//        ConfigReader.isTesting = false;
//        DatabaseConnection.getDBConnection().closeConnections();
//    }

}