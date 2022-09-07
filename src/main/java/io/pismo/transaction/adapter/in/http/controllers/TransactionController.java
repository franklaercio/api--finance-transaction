package io.pismo.transaction.adapter.in.http.controllers;

import io.pismo.transaction.adapter.in.http.controllers.data.request.CreateTransactionRequest;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(tags = "Transaction")
public interface TransactionController {

  @Operation(summary = "Create a new transaction")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully create a new transaction"),
      @ApiResponse(responseCode = "400", description = "Missing or invalid request body"),
      @ApiResponse(responseCode = "500", description = "Internal server error")}
  )
  ResponseEntity<Void> createTransaction(CreateTransactionRequest createTransactionRequest);

}
