package com.linkedin.replica.jobs.cache.handlers.impl;

import com.google.gson.Gson;
import com.linkedin.replica.jobs.cache.CacheConnection;
import com.linkedin.replica.jobs.config.Configuration;
import com.linkedin.replica.jobs.models.Job;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RedisJobsHandler implements JobsHandler{
    private static JedisPool cachePool;
    private Configuration configuration = Configuration.getInstance();
    private String CACHE_JOBS = configuration.getRedisConfigProp("cache.jobs.name");
    private Gson gson;
    public RedisJobsHandler() throws IOException, IOException {
        cachePool = CacheConnection.getInstance().getRedisPool();
        gson = CacheConnection.getGson();
    }
    public void saveJobListings(String userId, Object jobs) throws IOException {
        String key = CACHE_JOBS + ":" + userId;

        try {
            Jedis cacheInstance = cachePool.getResource();
            Pipeline pipeline = cacheInstance.pipelined();
            ArrayList<Job> jobsList = (ArrayList<Job>) jobs;
            for(Job job : jobsList) {
                String jsonObj = gson.toJson(jobsList);
                pipeline.sadd(key, jsonObj);
            }
            pipeline.sync();
            pipeline.close();
            cacheInstance.close();
        } catch (JedisException e) {
            e.printStackTrace();
            return;
        }
    }
    public List<Job> getJobListingsFromCache(String userId) throws IOException {
        String key = CACHE_JOBS + ":" + userId;

        try {
            Jedis cacheInstance = cachePool.getResource();
            ArrayList<Job> joblists = new ArrayList();
            Set<String> cacheResults = cacheInstance.smembers(key);
            for (String result : cacheResults) {
                Job object = gson.fromJson(result, Job.class);
                joblists.add(object);
            }
            cacheInstance.close();
            return joblists;
        } catch(JedisException e) {
            e.printStackTrace();
        }
        return null;
    }

    }
