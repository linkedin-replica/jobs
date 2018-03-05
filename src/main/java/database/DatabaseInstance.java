package database;

/**
 * A singleton class carrying a database instance
 */

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.model.AqlQueryOptions;
import com.arangodb.util.MapBuilder;
import com.arangodb.velocypack.VPackSlice;
import com.arangodb.velocypack.exception.VPackException;

public class DatabaseInstance {

    // TODO uncouple arango, read from some config file
    private static DatabaseHandler db;

    static {
        try {
            db = new ArangoHandler();
        } catch (IOException e) {
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
    public static DatabaseHandler getInstance() {
        return db;
    }
}