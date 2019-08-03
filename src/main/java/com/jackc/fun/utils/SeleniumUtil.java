package com.jackc.fun.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/8/2 15:34
 */
@Slf4j
public class SeleniumUtil {

    private static String cdpUrl = "http://localhost:%s/session/%s/goog/cdp/execute";

    public static String getResponseBody(String requestId,Integer port,ChromeDriver driver) {
        String url = String.format(cdpUrl,
                port, driver.getSessionId());
        RestTemplate restTemplate = new RestTemplate();
        Map map = new HashMap();
        Map paramMap = new HashMap();
        map.put("cmd","Network.getResponseBody");
        paramMap.put("requestId",requestId);
        map.put("params",paramMap);
        String s = restTemplate.postForObject(url, map, String.class);
        return s;
    }

    public static String clearCache(Integer port,WebDriver driver) {
        if(driver instanceof ChromeDriver){
            ChromeDriver chromeDriver = (ChromeDriver) driver;
            String url = String.format(cdpUrl,
                    port, chromeDriver.getSessionId());
            Map map = new HashMap();
            Map paramMap = new HashMap();
            map.put("cmd","Network.clearBrowserCache");
            map.put("params",paramMap);
            String s = HttpUtil.restTemplate.postForObject(url, map, String.class);
            return s;
        }
       return null;
    }

}
