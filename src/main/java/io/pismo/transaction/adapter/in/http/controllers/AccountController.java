package io.pismo.transaction.adapter.in.http.controllers;

import io.pismo.transaction.adapter.in.http.controllers.data.request.CreateAccountRequest;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}
