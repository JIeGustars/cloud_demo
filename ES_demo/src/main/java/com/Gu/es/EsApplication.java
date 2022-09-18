package com.Gu.es;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * 古文杰
 */
@SpringBootApplication
@EnableFeignClients
public class EsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsApplication.class,args);
    }

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        return new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://82.157.249.61:9200")
        ));
    }
}
