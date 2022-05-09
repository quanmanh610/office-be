package com.cmcglobal.backend.controller.impl;

import com.cmcglobal.backend.controller.AuthController;
import com.cmcglobal.backend.dto.request.auth.LoginRequest;
import com.cmcglobal.backend.service.PoaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class AuthControllerImpl implements AuthController {

    @Autowired
    private PoaService poaService;

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        return poaService.login(loginRequest);
    }

    @Override
    public ResponseEntity<?>  logout() {
        return poaService.logout();
    }
}
