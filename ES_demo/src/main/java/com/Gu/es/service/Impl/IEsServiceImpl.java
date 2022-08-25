package com.Gu.es.service.Impl;

import com.Gu.entity.User;
import com.Gu.es.service.IEsService;
import com.Gu.feign.clients.userClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service
public class IEsServiceImpl implements IEsService {

    @Resource
    private userClient client;

    @Override
    public User getById(int i) {
        return client.getUser(i);
    }
}
