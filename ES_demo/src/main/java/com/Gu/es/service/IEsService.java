package com.Gu.es.service;

import com.Gu.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
@Service
public interface IEsService {

    User getById(int i);
}
