package io.pismo.transaction.domain.usecases;

import io.pismo.transaction.domain.exceptions.BadRequestException;
import io.pismo.transaction.domain.models.Account;
import io.pismo.transaction.domain.port.in.AccountUseCase;
import io.pismo.transaction.domain.port.out.AccountDatabase;
import io.pismo.transaction.utils.StringUtils;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class AccountUseCaseImpl implements AccountUseCase {

  private final AccountDatabase accountDatabase;

  public AccountUseCaseImpl(AccountDatabase accountDatabase) {
    this.accountDatabase = accountDatabase;
  }

  @Override
  public void createAccount(String documentNumber) {

    if(StringUtils.isBlank(documentNumber) || StringUtils.length(documentNumber) != 11) {
      throw new BadRequestException("Account isn't valid, please check your data");
    }

    this.accountDatabase.createAccount(documentNumber);
  }

  @Override
  public Account findAccountById(Long accountId) {
    if(Objects.isNull(accountId)) {
      throw new BadRequestException("Account isn't valid, please check your data");
    }

    return this.accountDatabase.findAccountById(accountId);
  }
}
