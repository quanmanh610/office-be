package com.cmcglobal.backend.dto.poa;

import lombok.Data;

@Data
public class UserLogin {
    private String token;
    private UserInfo user;
    private String refreshToken;
    private Object menuTabs;
    private String department;
    private Object groups;
    private String code;
    private Boolean status;
    private String errorMessage;

    @Data
    public static class UserInfo {
        private String id;
        private String fullName;
        private String firstName;
        private String lastName;
        private String userName;
        private String email;
        private Object image;
        private String phone;
        private Object system;
        private Object roles;
        private Object permissions;
        private String dateOfBirth;
        private String externalInfo;
        private Object errorMessage;
        private Boolean status;
    }

}