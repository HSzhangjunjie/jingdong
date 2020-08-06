package com.elasticsearch.jingdong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * @author: HandSomeMaker
 * @date: 2020/8/4 21:18
 */
@Controller
public class IndexController {
    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }
}
