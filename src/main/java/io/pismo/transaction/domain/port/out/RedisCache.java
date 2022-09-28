package io.pismo.transaction.domain.port.out;

import java.util.Optional;

public interface RedisCache {

  void create(String prefix, String key, String value);

  Optional<String> find(String prefix, String key);

  void delete(String prefix, String key);
}
