package io.pismo.transaction.domain.usecases;

import com.google.gson.Gson;
import io.pismo.transaction.domain.exceptions.BadRequestException;
import io.pismo.transaction.domain.models.Operation;
import io.pismo.transaction.domain.models.enums.RedisKeyEnum;
import io.pismo.transaction.domain.port.in.OperationUseCase;
import io.pismo.transaction.domain.port.out.OperationDatabase;
import io.pismo.transaction.domain.port.out.RedisCache;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OperationUseCaseImpl implements OperationUseCase {

  private static final Gson gson = new Gson();

  private final OperationDatabase operationDatabase;
  private final RedisCache redisCache;

  public OperationUseCaseImpl(OperationDatabase operationDatabase, RedisCache redisCache) {
    this.operationDatabase = operationDatabase;
    this.redisCache = redisCache;
  }

  @Override
  public Operation findOperationById(Long operationId) {
    if (Objects.isNull(operationId)) {
      throw new BadRequestException("Operation isn't valid, please check your data");
    }

    Optional<String> cache = this.redisCache.find(
        RedisKeyEnum.CACHE_OPERATION.getValue(), operationId.toString());

    if (Objects.nonNull(cache) && cache.isPresent()) {
      return gson.fromJson(cache.get(), Operation.class);
    }

    Operation operation = this.operationDatabase.findOperationById(operationId);

    this.redisCache.create(RedisKeyEnum.CACHE_OPERATION.getValue(),
        operation.getId().toString(), gson.toJson(operation));

    return operation;
  }
}
