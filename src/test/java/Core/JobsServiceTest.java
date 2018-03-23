package Core;

import com.arangodb.ArangoDatabase;
import com.linkedin.replica.jobs.config.Configuration;
import com.linkedin.replica.jobs.database.DatabaseConnection;
import com.linkedin.replica.jobs.models.Job;
import com.linkedin.replica.jobs.services.JobService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class JobsServiceTest {
    private static JobService JobService;
    private static ArangoDatabase arangoDb;
    static Configuration config;

    @BeforeClass
    public static void init() throws IOException, SQLException {
        String rootFolder = "src/main/resources/config/";
        Configuration.init(rootFolder + "app.config", rootFolder +"arango.config",
                rootFolder +"database.config",rootFolder + "commands.config",
                rootFolder +"controller.config");
        DatabaseConnection.init();
        config = Configuration.getInstance();
        JobService = new JobService();
        arangoDb = DatabaseConnection.getInstance().getArangoDriver().db(
                Configuration.getInstance().getArangoConfigProp("db.name")
        );
    }

    @Before
    public void initBeforeTest() throws IOException {
        arangoDb.createCollection(
                config.getArangoConfigProp("collection.jobs.name")
        );
    }

    @Test
    public void testJobListingService() throws Exception {
        HashMap<String, String> args = new HashMap<>();
        args.put("jobId", "1");
        Job results = (Job) JobService.serve("job.listing",args);
        assertEquals("matching position name" , "Data Wrangling Engineer" ,results.getPositionName());
        assertEquals("matching company Name" , "DFKI" ,results.getCompanyName());
        assertEquals("matching industryType" , "Software" ,results.getIndustryType());
    }


}
