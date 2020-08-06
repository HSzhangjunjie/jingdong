package com.elasticsearch.jingdong.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @author: HandSomeMaker
 * @date: 2020/8/4 22:15
 */
@Configuration
public class ElasticsearchClientConfig {
    /**
     * 注入RestHighLevelClient对象
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.1.7", 9200, "http")
                )
        );
    }
}
