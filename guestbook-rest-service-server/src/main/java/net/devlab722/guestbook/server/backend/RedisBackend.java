package net.devlab722.guestbook.server.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Component
@Slf4j
public class RedisBackend {


    private Jedis jedisRead;
    private Jedis jedisWrite;

    @Autowired
    public RedisBackend(
            @Value("${guestbook.backend.redis.read.vip:localhost}") String redisReadVip,
            @Value("${guestbook.backend.redis.read.port:6379}") int redisReadPort,
            @Value("${guestbook.backend.redis.write.vip:localhost}") String redisWriteVip,
            @Value("${guestbook.backend.redis.write.port:6379}") int redisWritePort) {
        log.info("Initializing Jedis Read with host <{}> and port <{}>", redisReadVip, redisReadPort);
        this.jedisRead = new Jedis(redisReadVip, redisReadPort);
        log.info("Initializing Jedis Write with host <{}> and port <{}>", redisWriteVip, redisWritePort);
        this.jedisWrite = new Jedis(redisWriteVip, redisWritePort);
    }


    public void storeMessage(String message) {
        jedisWrite.lpush("messages", message);
    }

    public List<String> getAllMessages() {
        return jedisRead.lrange("messages", 0, -1);
    }

    public String pingReadBackend() {
        return jedisRead.ping();
    }

    public String pingWriteBackend() {
        return jedisWrite.ping();
    }
}
