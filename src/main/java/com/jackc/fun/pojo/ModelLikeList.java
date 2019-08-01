package com.jackc.fun.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by cj on 2019/7/28.
 */
@Configuration
@ConfigurationProperties
@Data
public class ModelLikeList {

    private List<ModelLike> models;

    @Data
    public static class ModelLike {
        private Integer uid;
        private Integer mode;
        private String nm;
    }
}
