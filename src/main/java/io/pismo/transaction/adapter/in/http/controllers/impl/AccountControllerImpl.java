package io.pismo.transaction.adapter.in.http.controllers.impl;

import io.pismo.transaction.adapter.in.http.controllers.AccountController;
import io.pismo.transaction.adapter.in.http.controllers.data.request.CreateAccountRequest;
import io.pismo.transaction.adapter.in.http.controllers.data.response.GetAccountResponse;
import io.pismo.transaction.domain.port.in.AccountUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountControllerImpl implements AccountController {

  private final AccountUseCase accountUseCase;

  public AccountControllerImpl(AccountUseCase accountUseCase) {
    this.accountUseCase = accountUseCase;
  }

  @Override
  public ResponseEntity<Void> createAccount(CreateAccountRequest createAccountRequest) {
    this.accountUseCase.createAccount(createAccountRequest.getDocumentNumber());

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Override
  public ResponseEntity<GetAccountResponse> getAccountById(int accountId) {
    return null;
  }
}
