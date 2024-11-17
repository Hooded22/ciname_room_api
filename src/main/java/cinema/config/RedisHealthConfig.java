package cinema.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisHealthConfig implements HealthIndicator {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisHealthConfig(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Health health() {
        try {
            RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
            conn.ping();
            conn.close();
            return Health.up().build();
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }
}