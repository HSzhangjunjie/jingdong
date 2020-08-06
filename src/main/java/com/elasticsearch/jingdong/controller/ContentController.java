package com.elasticsearch.jingdong.controller;

import com.elasticsearch.jingdong.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: HandSomeMaker
 * @date: 2020/8/4 22:14
 */
@RestController
public class ContentController {
    @Autowired
    private IContentService contentService;

    @GetMapping("/parse/{keywords}")
    public Boolean parse(@PathVariable String keywords) throws IOException {
        return contentService.parseContent(keywords);
    }

    @GetMapping("/search/{keywords}/{pageNo}/{pageSize}")
    public List<Map<String, Object>> search(@PathVariable String keywords,
                                            @PathVariable int pageNo,
                                            @PathVariable int pageSize) throws IOException {
        return contentService.searchPages(keywords, pageNo, pageSize);
    }
}
