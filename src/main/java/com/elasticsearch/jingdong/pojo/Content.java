package com.elasticsearch.jingdong.pojo;

import org.springframework.data.annotation.TypeAlias;

/**
 * @Description:
 * @author: HandSomeMaker
 * @date: 2020/8/4 22:00
 */
@TypeAlias("content")
public class Content {
    private String title;
    private String image;
    private String price;

    public Content(String title, String image, String price) {
        this.image = image;
        this.price = price;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Content{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
