package com.jackc.fun.selenium;

import com.jackc.fun.init.ProxyServer;
import com.jackc.fun.init.SeleniumInit;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/7/25 16:34
 */
@Service
@Slf4j
public class MfcWebsite {

    public void openIndex(){
        try {
            ProxyServer.proxy.newHar("swtestacademy");
            SeleniumInit.driver.get("https://www.myfreecams.com/");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Har har = ProxyServer.proxy.getHar();
            List<HarEntry> entries = har.getLog().getEntries();
            for (HarEntry entry : entries) {
                String url = entry.getRequest().getUrl();
                String text = entry.getResponse().getContent().getText();
                log.info("网站地址："+url);
                log.info("响应："+text);
            }
            File harFile = new File("d:\\data\\academy.har");
            har.writeTo(harFile);

        } catch (IOException ioe) {

        }
    }
}
