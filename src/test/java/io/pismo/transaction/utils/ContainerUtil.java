package io.pismo.transaction.utils;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@TestComponent
public class ContainerUtil {

  @Container
  public static GenericContainer<?> redis = new GenericContainer<>(
      DockerImageName.parse("redis:3.0.6"))
      .withExposedPorts(6379);

  static {
    redis.start();
  }

  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(
        redis.getHost(), redis.getFirstMappedPort());
    return new LettuceConnectionFactory(configuration);
  }
}
