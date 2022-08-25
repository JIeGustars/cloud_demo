package com.Gu.feign.clients;

import com.Gu.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "EsServer")
public interface esClient {

    @GetMapping("/es/add")
    String addRequest();

    @GetMapping("/es/get")
    User GetDocumentById();
}
