package com.jackc.fun.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/8/2 15:23
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResponseReceivedEventSmall {
    private String requestId;
    private double timestamp;
    private String type;
    private ResponseBean response;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ResponseBean {

        private String mimeType;
        private String protocol;
        private String securityState;
        private int status;
        private String statusText;
        private String url;
    }
}
