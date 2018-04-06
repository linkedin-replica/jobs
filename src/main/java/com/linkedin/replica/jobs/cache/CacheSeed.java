//package com.linkedin.replica.jobs.cache;
//
//import com.arangodb.ArangoDB;
//import com.arangodb.ArangoDBException;
//import com.arangodb.entity.BaseDocument;
//import com.google.gson.Gson;
//import com.linkedin.replica.jobs.config.Configuration;
//import com.linkedin.replica.jobs.database.DatabaseConnection;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.ParseException;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.Pipeline;
//import redis.clients.jedis.exceptions.JedisException;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.sql.SQLException;
//
//import static com.linkedin.replica.jobs.database.DatabaseSeed.getJSONData;
//
//public class CacheSeed {
//
//
//    private static JedisPool cachePool;
//    private static Configuration configuration = Configuration.getInstance();
//    private int databaseIndex = Integer.parseInt(configuration.getRedisConfigProp("cache.jobs.index"));
//    private static Gson gson;
//
//    public CacheSeed() throws FileNotFoundException, IOException, ParseException {
//        configuration = Configuration.getInstance();
//        cachePool = CacheConnection.getInstance().getRedisPool();
//        gson = CacheConnection.getGson();
//    }
//
//    public static void insertJobs() throws IOException, ParseException, SQLException, ClassNotFoundException {
//
//        String dbIndex = configuration.getRedisConfigProp("db.name");
//        try {
//            Jedis cacheInstance = cachePool.getResource();
//            cacheInstance.select(Integer.parseInt(dbIndex));
//            Pipeline pipeline = cacheInstance.pipelined();
//
//
//            pipeline.sync();
//            pipeline.close();
//            cacheInstance.close();
//        } catch (JedisException e) {
//            e.printStackTrace();
//            return;
//
//        }
//
//        int id = 0;
//        JSONArray jobs = getJSONData("src/main/resources/data/jobs.json");
//        for (Object job : jobs) {
//            JSONObject jobObject = (JSONObject) job;
//
//
//
//
//
//        }
//    }
//}
//
//
//
//    }
