package com.elasticsearch.jingdong.utils;

import com.elasticsearch.jingdong.pojo.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:Jsoup工具类
 * @author: HandSomeMaker
 * @date: 2020/8/4 21:34
 */
public class HtmlParseUtil {
    public static ArrayList<String> keywords=new ArrayList();
    static {
        keywords.add("sex");
    }
    public static List<Content> parseJD(String keywords) throws IOException {
        // 获取请求：https://search.jd.com/Search?keyword=keywords
        String url = "https://search.jd.com/Search?keyword=" + keywords;
        // 解析网页 返回Jsoup的document对象 所有JS的方法都可以调用
        Document document = Jsoup.parse(new URL(url), 30000);
        // 获取element元素
        Element element = document.getElementById("J_goodsList");
        // 获取所有的li元素
        Elements li = element.getElementsByTag("li");
        // 封装集合
        ArrayList<Content> contents = new ArrayList<>();
        // 获取元素的内容
        for (Element el : li) {
            String img = el.getElementsByTag("img").eq(0).attr("src");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            contents.add(new Content(title, img, price));
        }
        return contents;
    }
}
