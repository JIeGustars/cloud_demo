package com.Gu.es.service.Impl;

import com.Gu.entity.User;
import com.Gu.es.service.IEsService;
import com.Gu.feign.clients.userClient;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;


@Slf4j
@Service
public class IEsServiceImpl implements IEsService {

    @Resource
    private userClient userclient;

    @Resource
    private RestHighLevelClient client;

    @Override
    public User getById(int i) {
        return userclient.getUser(i);
    }


    @Override
    public void insertById(Integer id) throws IOException {
            User user = userclient.getUser(id);
            IndexRequest request = new IndexRequest("user").id(user.getId().toString());
            request.source(JSON.toJSONString(user), XContentType.JSON);
            client.index(request, RequestOptions.DEFAULT);
    }
}
