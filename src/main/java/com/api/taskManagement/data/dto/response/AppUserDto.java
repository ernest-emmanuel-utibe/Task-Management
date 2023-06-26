package com.api.taskManagement.data.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AppUserDto {
    private long id;
    private int code;
//    private Message message;
    private Boolean success;
    private String fullName;
}
