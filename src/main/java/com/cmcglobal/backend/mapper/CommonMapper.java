package com.cmcglobal.backend.mapper;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

@Component
public class CommonMapper {

    public String getFullName(UserRepresentation user) {
        return user.getFirstName().concat(" - ").concat(user.getLastName());
    }

    public String getDepartment(UserRepresentation user) {
        return user.getAttributes().get("department").get(0);
    }

    public String getGroup(UserRepresentation user) {
        return user.getAttributes().get("group").get(0);
    }

    public String getOwner(UserRepresentation owner) {
        return owner.getUsername() + "(" + this.getFullName(owner) + ")";
    }
}
