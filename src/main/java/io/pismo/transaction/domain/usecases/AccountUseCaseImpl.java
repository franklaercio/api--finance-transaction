package io.pismo.transaction.domain.usecases;

import com.google.gson.Gson;
import io.pismo.transaction.domain.exceptions.BadRequestException;
import io.pismo.transaction.domain.models.Account;
import io.pismo.transaction.domain.models.Operation;
import io.pismo.transaction.domain.port.in.AccountUseCase;
import io.pismo.transaction.domain.port.out.AccountDatabase;
import io.pismo.transaction.domain.port.out.RedisCache;
import io.pismo.transaction.utils.StringUtils;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AccountUseCaseImpl implements AccountUseCase {

  private static final Gson gson = new Gson();

  private final AccountDatabase accountDatabase;
  private final RedisCache redisCache;

  public AccountUseCaseImpl(AccountDatabase accountDatabase, RedisCache redisCache) {
    this.accountDatabase = accountDatabase;
    this.redisCache = redisCache;
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

    Optional<String> cache = this.redisCache.find("ACCOUNT-", accountId.toString());

    if(cache.isPresent()) {
      return gson.fromJson(cache.get(), Account.class);
    }

    Account account = this.accountDatabase.findAccountById(accountId);

    this.redisCache.create("ACCOUNT-", accountId.toString(), gson.toJson(account));

    return account;
  }
}
