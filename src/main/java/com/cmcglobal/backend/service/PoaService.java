package com.cmcglobal.backend.service;

import com.cmcglobal.backend.dto.request.auth.LoginRequest;
import com.cmcglobal.backend.entity.UserFlattened;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PoaService {
    List<String> getManagersUsername();

    List<String> getUsernameListByGroupName(String department);

    UserFlattened getUserInfoByUsername(String username);

    List<UserFlattened> getListUserInfoByUsername(String username);

    ResponseEntity<?> login(LoginRequest loginRequest);

    ResponseEntity<?>  logout();

    void syncUserData();
}
