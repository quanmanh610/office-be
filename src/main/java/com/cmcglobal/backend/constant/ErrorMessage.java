package com.cmcglobal.backend.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessage {

    public static final String SUCCESS = "Thành công";

    public static final String NOT_FOUND = "Resource not found ";
    public static final String FAILED = "Thất bại";
    public static final String SERVER_ERROR = "Lỗi hệ thống";
    public static final String NULL_DATA ="NULL_DATA";

    public static final String CREATE_TICKET_SUCCESS = "Create ticket success!";
    public static final String CREATE_TICKET_FAIL = "Create ticket fail! Something went wrong!";
    public static final String EXISTED_MEMBER_OCCUPIED = "Fail! You already have a seat!";
    public static final String DELETE_DOT_SUCCESSFULLY = "Delete dot successfully!";
    public static final String DELETE_DOT_FAIL = "Delete dot fail! Something went wrong!";
    public static final String UPDATE_SUCCESSFULLY = "Update successfully!";
    public static final String UPDATE_FAILED = "Update failed! Something went wrong!";
    public static final String USER_NOT_FOUND = "User not found!";
    public static final String SWAP_SUCCESSFULLY = "Swap successfully!";
    public static final String SUCCESSFULLY = "Successfully!";
    public static final String APPROVE_TICKET_FAILED = "Approve ticket failed! These dot of ticket have owner already.";

    public static final String DATE_FORMAT = "Date format error!";
    public static final String USER_NOT_FOUND_IN_GROUP = "Users not found in this department!";
    public static final String FLOOR_NOT_FOUND = "There are no floors found in this building!";

    public static final String BUILDING_ADDRESS_EXIST = "Building address already exists!";
    public static final String BUILDING_NAME_EXIST = "Building name already exists!";
    public static final String BUILDING_CREATED_SUCCESSFULLY = "Building created successfully!";
    public static final String FLOOR_NAME_EXIST = "Floor name already exists!";
    public static final String FLOOR_CREATED_SUCCESSFULLY = "Floor created successfully!";
    public static final String DELETE_TICKET_SUCCESSFULLY = "Delete ticket successfully!";
    public static final String DELETE_TICKET_FAILED = "Delete ticket failed!";
    public static final String TICKET_NOT_FOUND = "Ticket not found!";
    public static final String SEAT_EXISTED = "This user already has a seat";
}
