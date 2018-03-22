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
                "src/main/resources/config/database.config","src/main/resources/config/commands.config",
                "");
        config = Configuration.getInstance();
        mysqlHandler = new ArangoSQLJobsHandler();
        mysqlHandler.connect();

    }


    @Test
    public void getAppliedjobsTest() throws IOException, SQLException {
        ArrayList<String> ids = new ArrayList<String>();
        ids.add("1");
//        ids.add();
        ArrayList<String> jobs = mysqlHandler.getAppliedJobsIDs("32");
        assertEquals("Jobs must be of size 2" ,jobs.size() ,3);

    }


//    @AfterClass
//    public static void clean() throws IOException, SQLException, ClassNotFoundException {
//
//        DatabaseConnection.getDBConnection().closeConnections();
//    }

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