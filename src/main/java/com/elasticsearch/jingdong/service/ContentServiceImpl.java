package com.elasticsearch.jingdong.service;

import com.alibaba.fastjson.JSON;
import com.elasticsearch.jingdong.pojo.Content;
import com.elasticsearch.jingdong.utils.HtmlParseUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author: HandSomeMaker
 * @date: 2020/8/4 22:14
 */
@Controller
public class ContentServiceImpl implements IContentService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * @param keywords 关键词
     * @return 是否成功
     * @Description 解析数据放入ES索引
     */
    @Override
    public Boolean parseContent(String keywords) throws IOException {
        List<Content> contents = HtmlParseUtil.parseJD(keywords);
        // 把查询到的数据放入ES
        BulkRequest request = new BulkRequest();
        request.timeout("2m");
        for (Content content : contents) {
            request.add(new IndexRequest("jd_goods").source(JSON.toJSONString(content), XContentType.JSON));
        }
        BulkResponse response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        return !response.hasFailures();
    }

    /**
     * @param keywords 关键字
     * @param pageSize 总条数
     * @param pageNo   当前页数
     * @return 结果集
     * @Description 获取数据，实现搜索功能
     */
    @Override
    public List<Map<String, Object>> searchPages(String keywords, int pageNo, int pageSize) throws IOException {
        if (pageNo <= 1) {
            pageNo = 1;
        }
        if (!HtmlParseUtil.keywords.contains(keywords)) {
            parseContent(keywords);
        }
        // 条件搜索
        SearchRequest request = new SearchRequest("jd_goods");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 分页功能
        builder.from(pageNo);
        builder.size(pageSize);
        // 精准匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", keywords);
        builder.query(termQueryBuilder);
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        // 多个高亮显示
        highlightBuilder.requireFieldMatch(true);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        builder.highlighter(highlightBuilder);
        // 执行搜索
        request.source(builder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        // 解析结果
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (SearchHit documentFields : response.getHits().getHits()) {
            Map<String, HighlightField> highlightFieldMap = documentFields.getHighlightFields();
            HighlightField title = highlightFieldMap.get("title");
            Map<String, Object> source = documentFields.getSourceAsMap();
            // 替换高亮字段
            if (title != null) {
                Text[] fragments = title.fragments();
                String n_title = "";
                for (Text text : fragments) {
                    n_title += text;
                }
                source.put("title", n_title);
            }
            result.add(source);
        }
        return result;
    }
}
