package com.cmcglobal.backend.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DD_MM_YYYY = "dd-MM-yyyy";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DotType {
        public static final String SEAT_DOT = "seat";
        public static final String ROOM_DOT = "room";

    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class StatusType {
        public static final String AVAILABLE = "available";
        public static final String ALLOCATED = "allocated";
        public static final String OCCUPIED = "occupied";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Action {
        public static final String APPROVE = "approve";
        public static final String REJECT = "reject";
        public static final String PENDING = "pending";
    }
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TicketType {
        public static final String EXTEND = "extend";
        public static final String CLAIM = "claim";
        public static final String BOOK = "book";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Role {
        public static final String ADMIN = "OFFICE-ADMIN";
    }
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Group {
        public static final String DU_LEAD = "DU-LEAD";
    }
}
