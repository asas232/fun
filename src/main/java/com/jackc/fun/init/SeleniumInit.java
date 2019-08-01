package com.jackc.fun.init;

import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/7/25 16:31
 */
@Slf4j
public class SeleniumInit {

    public static WebDriver driver = null;

    public static void proxyDriver() {
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(ProxyServer.proxy);
        try {
            String hostIp = Inet4Address.getLocalHost().getHostAddress();
            seleniumProxy.setHttpProxy(hostIp + ":" + ProxyServer.proxy.getPort());
            seleniumProxy.setSslProxy(hostIp + ":" + ProxyServer.proxy.getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        DesiredCapabilities seleniumCapabilities = new DesiredCapabilities();
        seleniumCapabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        System.setProperty("webdriver.chrome.driver", "D:\\devsoft\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.merge(seleniumCapabilities);
        driver = new ChromeDriver(chromeOptions);
        ProxyServer.proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
    }

    public static void noproxyDriver() {
        if (InitSeting.isWindow) {
            System.setProperty("webdriver.chrome.driver", "D:\\devsoft\\chromedriver_win32\\chromedriver.exe");
//        }else {
//            System.setProperty("webdriver.chrome.driver", "/app/.apt/usr/bin/google-chrome");
//        }
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            if (!InitSeting.isWindow) {
                String binaryPath = null;
                try {
                    binaryPath = EnvironmentUtils.getProcEnvironment().get("GOOGLE_CHROME_SHIM");
                    log.info("binaryPath" + binaryPath);
                } catch (IOException e) {

                }
                chromeOptions.setBinary(binaryPath);
            }
            if (InitSeting.isWindow) {
                chromeOptions.addArguments("--proxy-server=socks5://" + "127.0.0.1:1080");
            }
            driver = new ChromeDriver(chromeOptions);
        }
    }
}
