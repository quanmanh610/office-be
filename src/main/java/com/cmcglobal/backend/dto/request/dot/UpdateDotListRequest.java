package com.cmcglobal.backend.dto.request.dot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDotListRequest {
    private List<Integer> id;
    private String fromDate;
    private String toDate;
    private String status;
}
