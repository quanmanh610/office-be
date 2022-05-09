package com.cmcglobal.backend.dto.response.dot;

import com.cmcglobal.backend.dto.response.Metadata;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDotResponse {
    private List<DotDTO> dotResponses;
    private Metadata metadata;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DotDTO {
        private String username;
        private String fullName;
        private String owner;
        private String department;
        private String group;
        private LocalDate fromDate;
        private LocalDate toDate;
        private Integer numberOfRentDays;
        private String status;
        private String type;
        private Boolean isActive;
        private String floorName;
        private String buildingName;
        private Float price;
    }
}
