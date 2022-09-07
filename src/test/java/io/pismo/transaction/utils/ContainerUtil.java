package io.pismo.transaction.utils;

import org.springframework.boot.test.context.TestComponent;
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
}
