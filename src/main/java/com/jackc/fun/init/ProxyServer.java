package com.jackc.fun.init;

import com.jackc.fun.pojo.ModelLikeList;
import com.jackc.fun.selenium.MfcWebsite;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/7/25 16:25
 */
@Service
public class ProxyServer implements CommandLineRunner {
    public static BrowserMobProxyServer proxy;
    @Autowired
    private ModelLikeList modelLikeList;

    @Override
    public void run(String... args) throws Exception {
//        proxy = new BrowserMobProxyServer();
//        proxy.start();
    }
}
