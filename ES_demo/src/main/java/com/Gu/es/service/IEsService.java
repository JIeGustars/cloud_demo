package com.Gu.es.service;


import com.Gu.es.entity.MeetingState;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface IEsService {


    void insertById(Integer Id) throws IOException;

    String queryFace(String userPhone, String meetingId) throws IOException;


    MeetingState queryUser(String meetingId, String phone) throws IOException;
}
