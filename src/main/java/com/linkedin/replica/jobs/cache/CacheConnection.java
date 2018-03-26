package com.linkedin.replica.jobs.cache;

import com.google.gson.Gson;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import com.linkedin.replica.jobs.config.Configuration;

import java.io.IOException;

public class CacheConnection {
    private Configuration configuration = Configuration.getInstance();
    private final String REDIS_IP = configuration.getAppConfigProp("redis.ip");
    private final int REDIS_PORT = Integer.parseInt(configuration.getAppConfigProp("redis.port"));
    private JedisPool redisPool;
    private static CacheConnection cache;
    private static Gson gson;
    private CacheConnection() throws IOException {
        redisPool = new JedisPool(new JedisPoolConfig(), REDIS_IP, REDIS_PORT);
        gson = new Gson();
    }

    /**
     * Get a singleton cache instance
     *
     * @return The cache instance
     */
    public static CacheConnection getInstance() throws IOException {
        if (cache == null) {
            synchronized (CacheConnection.class) {
                if (cache == null)
                    cache = new CacheConnection();
            }
        }
        return cache;
    }
    public static Gson getGson() throws IOException {
        if (gson == null) {
            if (gson == null)
                    gson = new Gson();

        }
        return gson;
    }
    public JedisPool getRedisPool() {
        return redisPool;
    }

    /** Destroys pool */
    public void destroyRedisPool(){
        redisPool.destroy();
    }
}