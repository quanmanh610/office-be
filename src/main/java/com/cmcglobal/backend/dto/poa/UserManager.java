package com.cmcglobal.backend.dto.poa;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserManager {
    private Integer totalRecords;
    private List<Object> messages;
    private Boolean status;
    private List<Item> item;

    @Getter
    @Setter
    public static class Item {
        private List<User> managers;
    }

    @Getter
    @Setter
    public static class User {
        private String username;
        private Boolean isActive;
    }
}
