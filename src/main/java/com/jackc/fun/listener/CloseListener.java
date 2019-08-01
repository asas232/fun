package com.jackc.fun.listener;

import com.jackc.fun.init.ProxyServer;
import com.jackc.fun.init.SeleniumInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/7/25 16:44
 */
@Component
@Slf4j
public class CloseListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
//        SeleniumInit.driver.quit();
//        ProxyServer.proxy.stop();
        log.info("程序停止");
    }
}
