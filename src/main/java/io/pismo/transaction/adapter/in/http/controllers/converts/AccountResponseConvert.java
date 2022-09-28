package io.pismo.transaction.adapter.in.http.controllers.converts;

import io.pismo.transaction.adapter.in.http.controllers.data.response.GetAccountResponse;
import io.pismo.transaction.domain.models.Account;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountResponseConvert implements Converter<Account, GetAccountResponse> {

  @Override
  public GetAccountResponse convert(Account source) {
    GetAccountResponse response = new GetAccountResponse();
    response.setAccountId(source.getAccountId());
    response.setDocumentNumber(source.getDocumentNumber());
    response.setAvailableCreditLimit(source.getAccountAvailableLimit());
    return response;
  }
}
