package io.pismo.transaction.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.pismo.transaction.domain.models.Operation;
import io.pismo.transaction.domain.models.enums.OperationTypeEnum;
import io.pismo.transaction.domain.port.out.OperationDatabase;
import io.pismo.transaction.domain.port.out.RedisCache;
import io.pismo.transaction.domain.usecases.OperationUseCaseImpl;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OperationUseCaseImplTest {

  @InjectMocks
  OperationUseCaseImpl operationUseCase;

  @Mock
  OperationDatabase operationDatabase;

  @Mock
  RedisCache redisCache;

  @Test
  void should_find_operation_by_id_with_no_cache() {
    when(redisCache.find(any(), any())).thenReturn(null);
    when(operationDatabase.findOperationById(any())).thenReturn(
        new Operation(2L, "COMPRA PARCELADA"));

    Operation operation = this.operationUseCase.findOperationById(2L);

    assertEquals(2L, operation.getId());
    assertEquals("COMPRA PARCELADA", operation.getDescription());

    verify(this.redisCache, times(1)).find(any(), any());
    verify(this.redisCache, times(1)).create(any(), any(), any());
    verify(this.operationDatabase, times(1)).findOperationById(any());
  }

  @Test
  void should_find_operation_by_id_with_cache() {
    when(redisCache.find(any(), any())).thenReturn(Optional.of(
        "{\"id\":4,\"description\":\"COMPRA PAGAMENTO\"}"));

    Operation operation = this.operationUseCase.findOperationById(4L);

    assertEquals(4L, operation.getId());
    assertEquals("COMPRA PAGAMENTO", operation.getDescription());

    verify(this.redisCache, times(1)).find(any(), any());
    verify(this.redisCache, never()).create(any(), any(), any());
    verify(this.operationDatabase, never()).findOperationById(any());
  }

  @Test
  void should_given_bad_request_if_operation_is_blank() {
    try {
      this.operationUseCase.findOperationById(null);
    } catch (Exception e) {
      assertEquals("Operation isn't valid, please check your data", e.getMessage());
    }

    verify(this.operationDatabase, never()).findOperationById(any());
  }

  @Test
  void should_given_bad_request_if_operation_is_not_exist() {
    try {
      OperationTypeEnum.fromValue("John Doe");
    } catch (Exception e) {
      assertEquals("Operation isn't valid, please check your data and contact us", e.getMessage());
    }
  }

}
