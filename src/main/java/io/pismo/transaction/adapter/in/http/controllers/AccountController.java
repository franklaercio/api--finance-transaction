package io.pismo.transaction.adapter.in.http.controllers;

import io.pismo.transaction.adapter.in.http.controllers.data.request.CreateAccountRequest;
import io.pismo.transaction.adapter.in.http.controllers.data.response.GetAccountResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "Account")
public interface AccountController {

  @PostMapping
  @Operation(summary = "Create a new account")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully create a new account"),
      @ApiResponse(responseCode = "400", description = "Missing or invalid request body"),
      @ApiResponse(responseCode = "500", description = "Internal server error")}
  )
  ResponseEntity<Void> createAccount(@RequestBody CreateAccountRequest createAccountRequest);

  @GetMapping("/accountId")
  @Operation(summary = "Get account by accountId")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully get a account"),
      @ApiResponse(responseCode = "400", description = "Missing or invalid parameter"),
      @ApiResponse(responseCode = "404", description = "Not found account"),
      @ApiResponse(responseCode = "500", description = "Internal server error")}
  )
  ResponseEntity<GetAccountResponse> getAccountById(@RequestParam(name = "accountId") Integer accountId);
}
