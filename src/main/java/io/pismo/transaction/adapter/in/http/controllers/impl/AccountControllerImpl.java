package io.pismo.transaction.adapter.in.http.controllers.impl;

import io.pismo.transaction.adapter.in.http.controllers.AccountController;
import io.pismo.transaction.adapter.in.http.controllers.converts.AccountResponseConvert;
import io.pismo.transaction.adapter.in.http.controllers.data.request.CreateAccountRequest;
import io.pismo.transaction.adapter.in.http.controllers.data.response.GetAccountResponse;
import io.pismo.transaction.domain.models.Account;
import io.pismo.transaction.domain.port.in.AccountUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountControllerImpl implements AccountController {

  private final AccountUseCase accountUseCase;
  private final AccountResponseConvert accountResponseConvert;

  public AccountControllerImpl(AccountUseCase accountUseCase,
      AccountResponseConvert accountResponseConvert) {
    this.accountUseCase = accountUseCase;
    this.accountResponseConvert = accountResponseConvert;
  }

  @Override
  @PostMapping
  public ResponseEntity<GetAccountResponse> createAccount(
      @RequestBody CreateAccountRequest createAccountRequest) {
    Account account = this.accountUseCase.createAccount(createAccountRequest.getDocumentNumber(),
        createAccountRequest.getAvailableCreditLimit());
    GetAccountResponse response = this.accountResponseConvert.convert(account);

    return ResponseEntity.ok(response);
  }

  @Override
  @GetMapping("/{accountId}")
  public ResponseEntity<GetAccountResponse> getAccountById(
      @PathVariable(name = "accountId") Long accountId) {
    Account account = this.accountUseCase.findAccountById(accountId);
    GetAccountResponse response = this.accountResponseConvert.convert(account);

    return ResponseEntity.ok(response);
  }
}
