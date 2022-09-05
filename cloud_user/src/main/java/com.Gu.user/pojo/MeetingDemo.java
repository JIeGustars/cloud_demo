package com.Gu.user.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingDemo {
    private String meetingId;
    private LocalDateTime createTime;
    private LocalDateTime endTime;
}
