package com.jackc.fun.selenium;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jackc.fun.pojo.MfcPerformanceVo;
import com.jackc.fun.service.MfcService;
import com.jackc.fun.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.CaptureType;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/7/25 14:44
 */
@Slf4j
public class Test {
    public static BrowserMobProxyServer proxy;
    private static final int COMMAND_TIMEOUT = 5000;
    // 必须固定端口，因为ChromeDriver没有实时获取端口的接口；
    private static final int CHROME_DRIVER_PORT = 9999;

    public static void main(String[] args) {
//      test1();
//      test2();
//      test3();
        recordVideo();
    }

    public static  void proxy(){

    }

    public static void test1(){
        System.setProperty("webdriver.chrome.driver", "D:\\devsoft\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--proxy-server=socks5://" + "127.0.0.1:1080");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.myfreecams.com");
//        try {
//            driver.findElement(By.xpath("//*[@id=\"enter_desktop\"]")).click();
//        } catch (Exception e) {
//            log.error("",e);
//        }
        try {
            log.info("--sssssssssssssssss");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String title = driver.getTitle();
        String scriptToExecute = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
        List<Map<String,Object>> o = (List<Map<String, Object>>) ((JavascriptExecutor) driver).executeScript(scriptToExecute);
        List<MfcPerformanceVo> mfcPerformanceVos = JsonUtil.convertValue(o,MfcPerformanceVo.class);
        for (MfcPerformanceVo mfcPerformanceVo : mfcPerformanceVos) {
            String name = mfcPerformanceVo.getName();
            if(name.contains("php/FcwExtResp.php") && name.contains("type=14")){
                log.info("人员信息url{}",name);
                MfcService mfcService = new MfcService();
                mfcService.getModelMSg(name);
            }
        }
//        String netData = ((JavascriptExecutor)driver).executeScript(scriptToExecute).toString();
        System.out.printf(title);
        driver.close();
    }

    public static void test2(){
        DesiredCapabilities d = DesiredCapabilities.chrome();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        d.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        System.setProperty("webdriver.chrome.driver", "D:\\devsoft\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--proxy-server=socks5://" + "127.0.0.1:1080");
//        WebDriver driver = new ChromeDriver(chromeOptions);
        WebDriver driver = new RemoteWebDriver(chromeOptions);
        driver.get("https://www.myfreecams.com");
        driver.findElement(By.xpath("//*[@id=\"enter_desktop\"]")).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        LogEntries les = driver.manage().logs().get(LogType.PERFORMANCE);
        for (LogEntry le : les) {
            log.info(le.getMessage());
        }
//        try {
//            log.info("--sssssssssssssssss");
//            Thread.sleep(600000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        String title = driver.getTitle();
        String scriptToExecute = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
        String netData = ((JavascriptExecutor)driver).executeScript(scriptToExecute).toString();
        log.info(netData);
        System.out.printf(title);
        driver.close();
    }


    public static void test3(){
        proxy = new BrowserMobProxyServer();
        proxy.start();
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        try {
            String hostIp = Inet4Address.getLocalHost().getHostAddress();
            seleniumProxy.setHttpProxy(hostIp + ":" + proxy.getPort());
            seleniumProxy.setSslProxy(hostIp + ":" + proxy.getPort());
            seleniumProxy.setSocksVersion(5);
            seleniumProxy.setSocksProxy("127.0.0.1:1080");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        DesiredCapabilities seleniumCapabilities = new DesiredCapabilities();
        seleniumCapabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        System.setProperty("webdriver.chrome.driver", "D:\\devsoft\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--headless");
//        chromeOptions.addArguments("--proxy-server=socks5://" + "127.0.0.1:1080");
        chromeOptions.merge(seleniumCapabilities);
        ChromeDriver driver = new ChromeDriver(chromeOptions);
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        try {
            proxy.newHar("swtestacademy");
            driver.get("https://www.huya.com/818929");
//              driver.get("https://www.myfreecams.com");
//              driver.findElement(By.xpath("//*[@id=\"enter_desktop\"]")).click();
//            driver.get("https://www.myfreecams.com");
            try {
                log.info("--sssssssssssssssss");
                Thread.sleep(600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Har har = proxy.getHar();
            List<HarEntry> entries = har.getLog().getEntries();
            for (HarEntry entry : entries) {
                String url = entry.getRequest().getUrl();
                String text = entry.getResponse().getContent().getText();
                log.info("网站地址："+url);
                if(url.contains("php/FcwExtResp.php")){
                    log.info("响应："+text);
                }
            }
            File harFile = new File("d:\\data\\academy.har");
            har.writeTo(harFile);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        driver.quit();
        proxy.stop();
    }
//    // 根据请求ID获取返回内容
//    public ResponseBodyVo getResponseBody(String requestId) {
//        ResponseBodyVo result = null;
//
//        try {
//            // CHROME_DRIVER_PORT chromeDriver提供的端口
//            String url = String.format("http://localhost:%s/session/%s/goog/cdp/execute",
//                    CHROME_DRIVER_PORT, getSessionId());
//
//            HttpPost httpPost = new HttpPost(url);
//            JSONObject object = new JSONObject();
//            JSONObject params = new JSONObject();
//            params.put("requestId", requestId);
//            object.put("cmd", "Network.getResponseBody");
//            object.put("params", params);
//
//            httpPost.setEntity(new StringEntity(object.toString()));
//
//            RequestConfig requestConfig = RequestConfig
//                    .custom()
//                    .setSocketTimeout(COMMAND_TIMEOUT)
//                    .setConnectTimeout(COMMAND_TIMEOUT).build();
//
//            CloseableHttpClient httpClient = HttpClientBuilder.create()
//                    .setDefaultRequestConfig(requestConfig).build();
//
//            HttpResponse response = httpClient.execute(httpPost);
//
//            JSONObject data = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
//            return JSONObject.toJavaObject(data, ResponseBodyVo.class);
//        } catch (IOException e) {
//            logger.error("getResponseBody failed!", e);
//        }
//
//        return result;
//    }
//
//
//    public static List<String> saveHttpTransferDataIfNecessary(ChromeDriver driver) {
//        Logs logs = driver.manage().logs();
//        Set<String> availableLogTypes = logs.getAvailableLogTypes();
//
//        if(availableLogTypes.contains(LogType.PERFORMANCE)) {
//            LogEntries logEntries = logs.get(LogType.PERFORMANCE);
//            List<ResponseReceivedEvent> responseReceivedEvents = new ArrayList<>();
//
//            for(LogEntry entry : logEntries) {
//                JSONObject jsonObj = JSON.parseObject(entry.getMessage()).getJSONObject("message");
//                String method = jsonObj.getString("method");
//                String params = jsonObj.getString("params");
//
//                if (method.equals(NETWORK_RESPONSE_RECEIVED)) {
//                    ResponseReceivedEvent response = JSON.parseObject(params, ResponseReceivedEvent.class);
//                    responseReceivedEvents.add(response);
//                }
//            }
//
//            doSaveHttpTransferDataIfNecessary(driver, responseReceivedEvents);
//        }
//    }

    public static void recordVideo(){
        String cmd = "E:\\Temp\\ffmpeg.exe -hide_banner -v fatal -i https://video354.myfreecams.com:443/NxServer/ngrp:mfc_111583707.f4v_mobile/playlist.m3u8?nc=0.004737445020210673 -c copy -vsync 2 -r 60 -b:v 500k E:\\Temp\\xxxcc.mp4";
        Runtime runtime = Runtime.getRuntime();
        List<Process> list = new ArrayList<>();
        try {
            Process exec = runtime.exec(cmd);
            list.add(exec);
        } catch (IOException e) {

        }
        log.info("------------");
        boolean a = true;
        try {
            log.info("--xxxxxxx");
            Thread.sleep(60000);
        } catch (InterruptedException e) {

        }
//        while (a){
//            try {
//                log.info("--xxxxxxx");
//                Thread.sleep(60000);
//            } catch (InterruptedException e) {
//
//            }
//            for (Process process : list) {
//                 if(!process.isAlive()){
//                     a = false;
//                 }
//            }
//        }

    }


}
