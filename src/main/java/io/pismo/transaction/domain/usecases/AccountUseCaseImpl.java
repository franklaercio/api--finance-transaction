package io.pismo.transaction.domain.usecases;

import com.google.gson.Gson;
import io.pismo.transaction.domain.exceptions.BadRequestException;
import io.pismo.transaction.domain.models.Account;
import io.pismo.transaction.domain.models.enums.RedisKeyEnum;
import io.pismo.transaction.domain.port.in.AccountUseCase;
import io.pismo.transaction.domain.port.out.AccountDatabase;
import io.pismo.transaction.domain.port.out.RedisCache;
import io.pismo.transaction.utils.StringUtils;
import java.math.BigDecimal;
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
  public Account createAccount(String documentNumber, BigDecimal availableCreditLimit) {

    if (StringUtils.isNotNumberOrBlank(documentNumber)
        || StringUtils.length(documentNumber) != 11) {
      throw new BadRequestException("Account isn't valid, please check your data");
    }

    return this.accountDatabase.createAccount(documentNumber, availableCreditLimit);
  }

  @Override
  public Account findAccountById(Long accountId) {
    if (Objects.isNull(accountId)) {
      throw new BadRequestException("Account isn't valid, please check your data");
    }

    Optional<String> cache = this.redisCache.find(
        RedisKeyEnum.CACHE_ACCOUNT.getValue(), accountId.toString());

    if (Objects.nonNull(cache) && cache.isPresent()) {
      return gson.fromJson(cache.get(), Account.class);
    }

    Account account = this.accountDatabase.findAccountById(accountId);

    this.redisCache.create(RedisKeyEnum.CACHE_ACCOUNT.getValue(),
        accountId.toString(), gson.toJson(account));

    return account;
  }

  @Override
  public void updateAccount(Account account) {
    this.redisCache.delete(RedisKeyEnum.CACHE_ACCOUNT.getValue(),
        account.getAccountId().toString());

    Account updatedAccount = this.accountDatabase.updateAccount(account);

    this.redisCache.create(RedisKeyEnum.CACHE_ACCOUNT.getValue(),
        updatedAccount.toString(), gson.toJson(account));
  }
}
