package pl.kithard.core.api.database;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

public class    RedisService {

    private final JedisPool pool;

    public RedisService(String uri) {
        this.pool = new JedisPool(uri);
    }

    public void set(String mapName, String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.hset(mapName, key, value);
        }
    }

    public String get(String mapName, String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.hget(mapName, key);
        }
    }

    public Map<String, String> getAll(String mapName) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.hgetAll(mapName);
        }
    }

    public void remove(String mapName, String key) {
        try (Jedis jedis = pool.getResource()) {
            jedis.hdel(mapName, key);
        }
    }
}
