package com.Gu.es.service;

import com.Gu.entity.User;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface IEsService {

    User getById(int i);

    void insertById(Integer id) throws IOException;

}
