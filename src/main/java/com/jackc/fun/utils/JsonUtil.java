package com.jackc.fun.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackc.fun.pojo.ResponseReceivedEventSmall;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by cj on 2019/7/26.
 */
@Slf4j
public class JsonUtil {
    public static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    public static <T>T parseJson(String json,Class<T> tClass){
        try {
            T t = objectMapper.readValue(json, tClass);
            return t;
        } catch (IOException e) {

        }
        return null;
    }
    public static String stringify(Object obj){
        try {
            String s = objectMapper.writeValueAsString(obj);
            return s;
        } catch (JsonProcessingException e) {

        }
        return null;
    }

    public static <T>T convertValue(Map map,Class<T> tClass){
        T t = objectMapper.convertValue(map, tClass);
        return t;
    }

    public static <T>List<T> convertValue(List<Map<String,Object>> list,Class<T> tClass){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, tClass);
        List<T> t = objectMapper.convertValue(list, javaType);
        return t;
    }

    public static <T> T pathToValue(String msg, Class<T> tClass , String ... paths){
        try {
            JsonNode jsonNode = objectMapper.readTree(msg);
            if(paths.length > 0){
                for (String path : paths) {
                    jsonNode = jsonNode.findPath(path);
                }
            }
            return objectMapper.treeToValue(jsonNode,tClass);
        } catch (IOException e) {
           return null;

        }
    }

    public static <T> T treeToValue(JsonNode jsonNode, Class<T> tClass, String ... paths){
        try {
            if(paths.length > 0){
                for (String path : paths) {
                    jsonNode = jsonNode.findPath(path);
                }
            }
            return objectMapper.treeToValue(jsonNode,tClass);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String msg ="{\"message\":{\"method\":\"Network.responseReceived\",\"params\":{\"frameId\":\"3DA59573A2F08856EDFC000C0820CD0A\",\"loaderId\":\"6DA55962C008E4B0C9DB07669C86A5AC\",\"requestId\":\"1000016836.380\",\"response\":{\"connectionId\":144,\"connectionReused\":true,\"encodedDataLength\":4244,\"fromDiskCache\":false,\"fromServiceWorker\":false,\"headers\":{\"accept-ranges\":\"bytes\",\"access-control-allow-origin\":\"*\",\"cache-control\":\"max-age=172800\",\"content-length\":\"4024\",\"content-type\":\"image/jpeg\",\"date\":\"Fri, 02 Aug 2019 07:05:49 GMT\",\"etag\":\"\\\"2354025868\\\"\",\"last-modified\":\"Thu, 14 Feb 2019 02:44:47 GMT\",\"server\":\"nginx\",\"status\":\"200\",\"x-cache\":\"HIT\",\"x-cache-hits\":\"172, 2\",\"x-served-by\":\"edge4tyo\"},\"mimeType\":\"image/jpeg\",\"protocol\":\"h2\",\"remoteIPAddress\":\"127.0.0.1\",\"remotePort\":1080,\"securityDetails\":{\"certificateId\":0,\"certificateTransparencyCompliance\":\"unknown\",\"cipher\":\"AES_128_GCM\",\"issuer\":\"DigiCert SHA2 Secure Server CA\",\"keyExchange\":\"ECDHE_RSA\",\"keyExchangeGroup\":\"P-256\",\"protocol\":\"TLS 1.2\",\"sanList\":[\"*.mfcimg.com\",\"mfcimg.com\"],\"signedCertificateTimestampList\":[],\"subjectName\":\"*.mfcimg.com\",\"validFrom\":1501545600,\"validTo\":1602072000},\"securityState\":\"secure\",\"status\":200,\"statusText\":\"\",\"timing\":{\"connectEnd\":-1,\"connectStart\":-1,\"dnsEnd\":-1,\"dnsStart\":-1,\"proxyEnd\":-1,\"proxyStart\":-1,\"pushEnd\":0,\"pushStart\":0,\"receiveHeadersEnd\":73.301,\"requestTime\":107795.416283,\"sendEnd\":18.042,\"sendStart\":7.137,\"sslEnd\":-1,\"sslStart\":-1,\"workerReady\":-1,\"workerStart\":-1},\"url\":\"https://img.mfcimg.com/photos2/313/31321885/avatar.90x90.jpg\"},\"timestamp\":107795.490365,\"type\":\"Image\"}},\"webview\":\"FA6E8BDB90751A28056501DC8541925C\"}";
        try {
            JsonNode jsonNode = JsonUtil.objectMapper.readTree(msg);
            String method = JsonUtil.treeToValue(jsonNode, String.class, "message", "method");
            ResponseReceivedEventSmall responseReceivedEvent = JsonUtil.treeToValue(jsonNode, ResponseReceivedEventSmall.class, "message", "params");
            log.info("{}",responseReceivedEvent);
        } catch (IOException e) {

        }

    }


}
