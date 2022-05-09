package com.cmcglobal.backend.dto.request.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayloadDTO {
    private loginDTO payload;

    @Getter
    @Setter
    public static class loginDTO{
        private String username;
        private String password;
        private String key = "office";
        private String secret= "OFFICE-CMC";
    }
}
