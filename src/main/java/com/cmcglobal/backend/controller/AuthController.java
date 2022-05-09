package com.cmcglobal.backend.controller;

import com.cmcglobal.backend.dto.request.auth.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("api/v1/auth")
public interface AuthController {
    @Operation(
            summary = "login",
            description = "login",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest);

    @Operation(
            summary = "logout",
            description = "logout",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @PostMapping("logout")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> logout();
}