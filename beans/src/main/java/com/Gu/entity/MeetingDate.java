package com.Gu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingDate {
    private Integer Id;
    private String userId;
    private String meetingId;
    private LocalDateTime createTime;
    private LocalDateTime endTime;
}
