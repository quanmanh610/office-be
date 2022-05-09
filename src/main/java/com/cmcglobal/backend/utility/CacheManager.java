package com.cmcglobal.backend.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class CacheManager {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void set(String key, Object value) throws IOException {
        redisTemplate.opsForValue().set(key, JsonParser.toJson(value));
    }

    public void set(String key, Object value, int expireTime) throws IOException {
        redisTemplate.opsForValue().set(key, JsonParser.toJson(value), expireTime, TimeUnit.HOURS);
    }

    public <T> T get(String key, Class<T> tClass) throws Exception {
        try {
            String value = redisTemplate.opsForValue().get(key);
            return JsonParser.entity(value, tClass);
        } catch (Exception e) {
            return null;
        }
    }

    public String get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            return null;
        }
    }

    public <T> ArrayList<T> getList(String key, Class<T> tClass) throws Exception {
        try {

            String value = redisTemplate.opsForValue().get(key);
            return JsonParser.arrayList(value, tClass);

        } catch (Exception e) {

            return null;
        }
    }

    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public void del(String key) {
        redisTemplate.delete(key);
    }

    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
}