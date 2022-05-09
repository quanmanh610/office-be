package com.cmcglobal.backend.dto.request.dot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDotMapRequest {
    private Integer id;
    private String username;
    private String status;
}
