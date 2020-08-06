package com.elasticsearch.jingdong.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: HandSomeMaker
 * @date: 2020/8/4 22:14
 */
public interface IContentService {
    Boolean parseContent(String keywords) throws IOException;

    List<Map<String, Object>> searchPages(String keywords, int pageNo, int pageSize) throws IOException;
}
