package com.cmcglobal.backend.dto.request.dot;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDotRequest {
    private String status;

    private String type;

    private Float coordinateX;

    private Float coordinateY;

    private boolean isActive;
}
