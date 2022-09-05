package io.pismo.transaction.adapter.out.databases.converts;

import io.pismo.transaction.adapter.out.databases.entities.AccountEntity;
import io.pismo.transaction.domain.models.Account;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GetAccountConvert implements Converter<AccountEntity, Account> {

  @Override
  public Account convert(AccountEntity source) {
    return new Account(source.getId(), source.getDocumentNumber());
  }
}
