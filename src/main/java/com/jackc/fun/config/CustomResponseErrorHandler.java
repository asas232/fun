package com.jackc.fun.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Description
 * @Author chenjie
 * @DATE 2018/6/21 16:18
 */
@Slf4j
public class CustomResponseErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return super.hasError(clientHttpResponse);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        if(statusCode.series() == HttpStatus.Series.SERVER_ERROR){
            String s = StreamUtils.copyToString(response.getBody(), Charset.forName("utf-8"));
            log.info("服务方5xx错误:"+s);
            throw new HttpServerErrorException(statusCode, response.getStatusText(), response.getHeaders(), this.getResponseBody(response), this.getCharset(response));
        }else if(statusCode.series() == HttpStatus.Series.CLIENT_ERROR){
            if(statusCode == HttpStatus.NOT_FOUND){
                throw new HttpClientErrorException(statusCode, response.getStatusText(), response.getHeaders(), this.getResponseBody(response), this.getCharset(response));
            }else {
                String s = StreamUtils.copyToString(response.getBody(), Charset.forName("utf-8"));
                log.info("服务方4xx错误:"+s);
                throw new HttpClientErrorException(statusCode, response.getStatusText(), response.getHeaders(), this.getResponseBody(response), this.getCharset(response));
            }

        }else {
            super.handleError(response);
        }
    }
}
