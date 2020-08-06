package com.elasticsearch.jingdong;

import com.elasticsearch.jingdong.service.IContentService;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class JingdongApplicationTests {
    @Autowired
    private IContentService service;
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    void createIndexTest() throws IOException {
        // 创建索引请求
        CreateIndexRequest request=new CreateIndexRequest("jd_goods");
        // 执行创建请求 获得请求响应
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        // 输出响应
        System.out.println(createIndexResponse);
    }

    @Test
    void deleteIndexTest() throws IOException {
        // 创建删除索引请求
        DeleteIndexRequest request=new DeleteIndexRequest("jd_goods");
        // 执行删除请求 获得删除响应
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        // 输出删除结果
        System.err.println(acknowledgedResponse);
    }

    @Test
    void parseTest() throws IOException {
        service.parseContent("sex");
    }

}
