package com.jackc.fun.init;

import com.jackc.fun.pojo.ModelLikeList;
import com.jackc.fun.service.MfcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by cj on 2019/7/28.
 */
@Service
@Slf4j
public class InitSeting implements CommandLineRunner {
    @Autowired
    private ModelLikeList modelLikeList;
    public static boolean isWindow = false;

    @Override
    public void run(String... strings) throws Exception {
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")){
            InitSeting.isWindow =  true;
         }
        File file = new File("/tmp/app-initialized");
//        file.mkdirs();
        file.createNewFile();
        MfcService.modelLikeList = modelLikeList;
        log.info("启动selenium");
//        SeleniumInit.noproxyDriver();
//        SeleniumInit.chromeServiceDriver();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                MfcService.open();
//            }
//        };
//        new Thread(runnable).start();
    }
}
