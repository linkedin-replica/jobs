package database;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import models.Company;
import models.Job;
import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
//import com.arangodb.ArangoDB;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.model.AqlQueryOptions;
import com.arangodb.util.MapBuilder;
import com.arangodb.velocypack.VPackSlice;
import com.arangodb.velocypack.exception.VPackException;
import models.lightJobListing;
import models.lightPost;
import models.lightUser;

public class ArangoHandler implements DatabaseHandler{
ArangoDB arangoDB;

    public void connect() {
        // TODO
        arangoDB = new ArangoDB.Builder().build();
    }

  
    public void disconnect() {
        // TODO
    }

    public List<Job> getAppliedjobs(){

    }
}
