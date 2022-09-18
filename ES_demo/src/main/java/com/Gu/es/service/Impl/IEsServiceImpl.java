package com.Gu.es.service.Impl;


import com.Gu.es.entity.MeetingState;
import com.Gu.es.feign.userClient;
import com.Gu.es.service.IEsService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
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
    public void insertById(Integer Id) throws IOException {
            MeetingState user = userclient.getUser(Id);
            IndexRequest request = new IndexRequest("user").id(user.getId().toString());
            request.source(JSON.toJSONString(user), XContentType.JSON);
            client.index(request, RequestOptions.DEFAULT);
    }

    @Override
    public MeetingState queryUser(String meetingId, String phone) throws IOException {
        // 1.准备Request
        SearchRequest request = new SearchRequest("user");
        // 2.准备DSL
        // 2.1.准备BooleanQuery
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 2.2.添加term
        boolQuery.must(QueryBuilders.termQuery("userPhone", phone));
        // 2.3.添加range
        boolQuery.filter(QueryBuilders.termQuery("meetingId",meetingId));
        request.source().query(boolQuery);
        // 3.发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4.解析响应
        System.out.println(handleResponse(response));
        return handleResponse(response);
    }

    @Override
    public String queryFace(String userPhone, String meetingId) throws IOException {
        // 1.准备Request
        SearchRequest request = new SearchRequest("user");
        // 2.准备DSL
        // 2.1.准备BooleanQuery
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 2.2.添加term
        boolQuery.must(QueryBuilders.termQuery("userPhone", userPhone));
        // 2.3.添加range
        boolQuery.filter(QueryBuilders.termQuery("meetingId",meetingId));
        request.source().query(boolQuery);
        // 3.发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4.解析响应
        return handleResponse(response).getFace();
    }
    private MeetingState handleResponse(SearchResponse response) {
        // 4.解析响应
        SearchHits searchHits = response.getHits();
        // 4.1.获取总条数
        long total = searchHits.getTotalHits().value;
        System.out.println("共搜索到" + total + "条数据");
        // 4.2.文档数组
        SearchHit[] hits = searchHits.getHits();
        // 4.3.遍历
        MeetingState meeting = new MeetingState();
        for (SearchHit hit : hits) {
            // 获取文档source
            String json = hit.getSourceAsString();
            // 反序列化
             meeting = JSON.parseObject(json, MeetingState.class);
        }
        return meeting;
    }

}
