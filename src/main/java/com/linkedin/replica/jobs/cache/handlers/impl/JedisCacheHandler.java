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
import java.lang.reflect.Field;
import java.util.*;

public class JedisCacheHandler implements JobsHandler {

    private static JedisPool cachePool;
    private Configuration configuration = Configuration.getInstance();
    private int databaseIndex = Integer.parseInt(configuration.getRedisConfigProp("cache.jobs.index"));
    private Gson gson;

    public JedisCacheHandler() throws IOException, IOException {
        cachePool = CacheConnection.getInstance().getRedisPool();
        gson = CacheConnection.getGson();
    }

    @Override
    public void saveJobListing(String[] jobIds, Object jobs) throws IOException {

        try {
        Jedis cacheInstance = cachePool.getResource();
        cacheInstance.select(databaseIndex);
        Pipeline pipeline = cacheInstance.pipelined();
        ArrayList<Object> jobsList = (ArrayList<Object>) jobs;
        for(int j = 0; j < jobIds.length; ++j) {
            String key = jobIds[j];
            Object job = jobsList.get(j);
            Class jobClass = job.getClass();
            Field [] fields = jobClass.getDeclaredFields();
            for(int i = 0; i < fields.length; ++i) {
                String fieldName = fields[i].getName();
                Object fieldValue;
                try {
                    fieldValue = fields[i].get(job);
                    pipeline.hset(key, fieldName, gson.toJson(fieldValue));
                } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
        }
            pipeline.sync();
            pipeline.close();
            cacheInstance.close();
        } catch (JedisException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public Object getJobListingFromCache(String key, Class<?> tClass) throws IOException {

        try {
            Jedis cacheInstance = cachePool.getResource();
            cacheInstance.select(Integer.parseInt(configuration.getRedisConfigProp("cache.jobs.index")));
            if(!cacheInstance.exists(key))
                return null;
            Class jobClass = tClass;
            Object job = null;
            try {
                job = jobClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Field [] fields = jobClass.getDeclaredFields();
            for(int i = 0; i < fields.length; ++i) {
                String fieldName = fields[i].getName();
                String jsonValue = cacheInstance.hget(key, fieldName);
                Object objectValue = gson.fromJson(jsonValue, fields[i].getType());
                try {
                    jobClass.getField(fieldName).set(job, objectValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            cacheInstance.close();
            return job;
        } catch(JedisException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteJobListing(String key) {
        try {
            Jedis cacheInstance = cachePool.getResource();
            cacheInstance.select(databaseIndex);
            if(!cacheInstance.exists(key))
                return;
            String [] fieldNames = cacheInstance.hgetAll(key).keySet().toArray(new String[cacheInstance.hgetAll(key).keySet().size()]);
            cacheInstance.hdel(key, fieldNames);
        } catch(JedisException e) {
            e.printStackTrace();
        }
    }

    public void editJobListing(String key, LinkedHashMap<String, String> args) {
        try {
            Jedis cacheInstance = cachePool.getResource();
            cacheInstance.select(databaseIndex);
            if(!cacheInstance.exists(key))
                return;
            for(Map.Entry<String, String> entry : args.entrySet()) {
                cacheInstance.hset(key, entry.getKey(), entry.getValue());
            }
        } catch(JedisException e) {
            e.printStackTrace();
        }
    }


}
