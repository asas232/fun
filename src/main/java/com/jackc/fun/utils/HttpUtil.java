package com.jackc.fun.utils;

import com.jackc.fun.config.CustomResponseErrorHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/8/3 11:04
 */
public class HttpUtil {

    public static RestTemplate restTemplate;

    static {
        restTemplate = restTemplate();
    }

    private static HttpComponentsClientHttpRequestFactory customHttpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpComponentsClient());
    }

    private static RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(customHttpRequestFactory());
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        return restTemplate;
    }

    private static HttpClient httpComponentsClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(150);
        connectionManager.setDefaultMaxPerRoute(10);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000)
                .setConnectTimeout(5000).setConnectionRequestTimeout(5000).build();

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager).build();
    }


}
