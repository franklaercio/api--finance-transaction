package io.pismo.transaction.adapter.in.http.controllers.impl;

import io.pismo.transaction.adapter.in.http.controllers.TransactionController;
import io.pismo.transaction.adapter.in.http.controllers.data.request.CreateTransactionRequest;
import io.pismo.transaction.domain.port.in.TransactionUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionControllerImpl implements TransactionController {

  private final TransactionUseCase transactionUseCase;

  public TransactionControllerImpl(TransactionUseCase transactionUseCase) {
    this.transactionUseCase = transactionUseCase;
  }

  @Override
  @PostMapping
  public ResponseEntity<Void> createTransaction(
      @RequestBody CreateTransactionRequest createTransactionRequest) {
    this.transactionUseCase.createTransaction(createTransactionRequest.getAcccountId(),
        createTransactionRequest.getOperationId(), createTransactionRequest.getAmount());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
