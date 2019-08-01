package com.jackc.fun.pojo;

import lombok.Data;

import java.util.List;

/**
 * Created by cj on 2019/7/26.
 */
@Data
public class MfcPerformanceVo {

    private Integer redirectCount;
    private Integer encodedBodySize;
    private Integer unloadEventEnd;
    private Double responseEnd;
    private Double domainLookupEnd;
    private Integer unloadEventStart;
    private Double domContentLoadedEventStart;
    private String type;
    private Integer decodedBodySize;
    private Double duration;
    private Integer redirectStart;
    private Double connectEnd;
    private Double requestStart;
    private Integer startTime;
    private Double fetchStart;
    private List<?> serverTiming;
    private Double domContentLoadedEventEnd;
    private String entryType;
    private Integer workerStart;
    private Double responseStart;
    private Double domIntegereractive;
    private Double domComplete;
    private Double domainLookupStart;
    private Integer redirectEnd;
    private Integer transferSize;
    private Double connectStart;
    private Double loadEventStart;
    private Integer secureConnectionStart;
    private String name;
    private String nextHopProtocol;
    private String initiatorType;
    private Double loadEventEnd;
}
