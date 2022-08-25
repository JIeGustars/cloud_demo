package com.Gu.es.controller;

import com.Gu.entity.User;
import com.Gu.es.service.IEsService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("es")
@Slf4j
@ResponseBody
public class EsController {


    @Resource
    private RestHighLevelClient client;

    @Resource
    private IEsService iEsService;

    @GetMapping("add")
    public String  addRequest() throws IOException {
        // 1.根据id查询酒店数据
        User user = iEsService.getById(1);
        // 3.将HotelDoc转json
        String json = JSON.toJSONString(user);
        System.out.println("JSON:"+json);
        // 1.准备Request对象
        IndexRequest request = new IndexRequest("user").id(user.getId().toString());
        // 2.准备Json文档
        request.source(json, XContentType.JSON);
        // 3.发送请求
        client.index(request, RequestOptions.DEFAULT);
        return "添加成功";
    }

    @GetMapping("get")
    public User GetDocumentById() throws IOException {
        GetRequest request = new GetRequest("user","1");
        GetResponse response = client.get(request,RequestOptions.DEFAULT);
        String json = response.getSourceAsString();
        return JSON.parseObject(json,User.class);
    }
}
