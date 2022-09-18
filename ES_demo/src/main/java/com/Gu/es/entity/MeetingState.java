package com.Gu.es.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingState {
    private Integer Id;
    private String meetingId;
    private String userName;
    private String userState;
    private String userPhone;
    private String face;
}
