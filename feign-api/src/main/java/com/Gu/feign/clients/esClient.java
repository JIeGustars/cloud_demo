package com.Gu.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "EsServer")
public interface esClient {


}
