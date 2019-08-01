package com.jackc.fun.service;

import com.jackc.fun.init.InitSeting;
import com.jackc.fun.init.SeleniumInit;
import com.jackc.fun.pojo.MfcModelVo;
import com.jackc.fun.pojo.MfcPerformanceVo;
import com.jackc.fun.pojo.ModelLikeList;
import com.jackc.fun.utils.DateUtil;
import com.jackc.fun.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.jackc.fun.init.SeleniumInit.driver;

/**
 * Created by cj on 2019/7/26.
 */
@Service
@Slf4j
public class MfcService {

    @Autowired
    private RestTemplate restTemplate;

    public  static ModelLikeList modelLikeList;
    public  static List<MfcModelVo> likeOnlineModels = new ArrayList<>();
    public  static List<MfcModelVo> OnlineModels = new ArrayList<>();
    public  static Map<Integer,Process> processMap = new HashMap<>();


    @Scheduled(cron="0 */2 * * * ?")
    public static void open(){
        if(driver == null){
            log.info("--driver----");
            return;
        }
        try {
            log.info("--1111111");
            driver.get("https://www.myfreecams.com");
//            log.info("--2222222");
//            driver.findElement(By.xpath("//*[@id=\"enter_desktop\"]")).click();
            log.info("--3333333");
        } catch (Exception e) {
            log.error("",e);
        }
        try {
            log.info("--扫描");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.error("",e);
        }
        try {
            String scriptToExecute = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
            List<Map<String,Object>> o = (List<Map<String, Object>>) ((JavascriptExecutor) driver).executeScript(scriptToExecute);
            List<MfcPerformanceVo> mfcPerformanceVos = JsonUtil.convertValue(o,MfcPerformanceVo.class);
            log.info("performance数量："+mfcPerformanceVos.size());
            for (MfcPerformanceVo mfcPerformanceVo : mfcPerformanceVos) {
                String name = mfcPerformanceVo.getName();
                if(name.contains("php/FcwExtResp.php") && name.contains("type=14")){
                    log.info(name);
                    MfcService mfcService = new MfcService();
                    mfcService.getModelMSg(name);
                }
            }
        } catch (Exception e) {
            log.error("",e);
        }
//        driver.close();
    }


    public void getModelMSg(String url){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            analyseContent(result);
        } catch (RestClientException e) {
            log.error("",e);
        }
    }

    public void analyseContent(String result){
        Map<String,Object> map = JsonUtil.parseJson(result, Map.class);
        Integer type = Integer.valueOf(map.get("type").toString());
        if(type != 21){
            return;
        }
        List<List> rdata = (List<List>) map.get("rdata");
        int i =0;
        List<MfcModelVo> modelList = new ArrayList<>();
        for (List model : rdata) {
            if(i!=0){
                MfcModelVo mfcModelVo = new MfcModelVo();
                Integer vs = Integer.valueOf(model.get(3).toString());
                if(vs == 0 || vs == 90 ){
                    String name = model.get(0).toString();
                    Integer uid = Integer.valueOf(model.get(2).toString());
                    Integer roomId = uid+100000000;
                    Integer rommServer = Integer.valueOf(model.get(6).toString())-500;
                    mfcModelVo.setName(name);
                    mfcModelVo.setUid(uid);
                    mfcModelVo.setRommId(roomId);
                    mfcModelVo.setRommServer(rommServer);
                    modelList.add(mfcModelVo);
                }
            }
            i++;

        }
        OnlineModels = modelList;
        analyseLikeModelOnline(modelList);
        log.info("model-------------------------");

    }

    public void analyseLikeModelOnline(List<MfcModelVo> modelList){
        Map<Integer, ModelLikeList.ModelLike> uidMap = modelLikeList.getModels()
                .stream().collect(Collectors.toMap(ModelLikeList.ModelLike::getUid, Function.identity(), (k1, k2) -> k2));
        Map<String, ModelLikeList.ModelLike> nameMap = modelLikeList.getModels()
                .stream().collect(Collectors.toMap(ModelLikeList.ModelLike::getNm, Function.identity(), (k1, k2) -> k2));
        likeOnlineModels.clear();
//        Map<Integer, MfcModelVo> uidOnlineMap = likeOnlineModels
//                .stream().collect(Collectors.toMap(MfcModelVo::getUid, Function.identity(), (k1, k2) -> k2));
        for (MfcModelVo mfcModelVo : modelList) {
            Integer uid = mfcModelVo.getUid();
            String name = mfcModelVo.getName();
            if(uidMap.containsKey(uid) || nameMap.containsKey(name)){
                likeOnlineModels.add(mfcModelVo);
            }
        }

        if(!CollectionUtils.isEmpty(likeOnlineModels)){
            log.info(JsonUtil.stringify("在线---"+likeOnlineModels));
        }

        recordModelOnline();
    }

    public void recordModelOnline(){
        if(!CollectionUtils.isEmpty(likeOnlineModels)){
            for (MfcModelVo onlineModel : likeOnlineModels) {
                Integer uid = onlineModel.getUid();
                if(!processMap.containsKey(uid)){
                    String path ;
                    String fileName = DateUtil.dateToStr(new Date(), "yyyyMMdd-HHmmss") + "-" + onlineModel.getName() + ".mp4";
                    String nc ="0."+ RandomUtils.nextLong(00124123434363232L, 94539193545354354L);
                    String url = "https://video{0}.myfreecams.com:443/NxServer/ngrp:mfc_{1}.f4v_mobile/playlist.m3u8?nc={2}";
//                    String cmd = "E:\\Temp\\ffmpeg.exe -hide_banner -v fatal -i {0} -c copy -vsync 2 -r 60 -b:v 500k {1}";
                    String cmd;
                    if(InitSeting.isWindow){
                        path = "E:\\Temp\\"+ fileName;
                        cmd = "E:\\soft\\streamlink\\bin\\streamlink.exe {0} best -o {1}";
                    }else {
                        path = "/app/captures/"+ fileName;
                        cmd = "ffmpeg -hide_banner -v fatal -i {0} -c copy -vsync 2 -r 60 -b:v 500k {1}";
                    }
                    url = MessageFormat.format(url,String.valueOf(onlineModel.getRommServer()),String.valueOf(onlineModel.getRommId()),nc);
                    cmd = MessageFormat.format(cmd,url,path);
                    log.info("录制视频："+cmd);
                    Runtime runtime = Runtime.getRuntime();
                    try {
                        Process exec = runtime.exec(cmd);
                        processMap.put(uid,exec);
                    } catch (IOException e) {

                    }
                }
            }
        }
    }

    @Scheduled(cron="0 */1 * * * ?")
    public void checkRecordModelProcess() {
        try {
            Map<Integer, MfcModelVo> uidMap = likeOnlineModels
                    .stream().collect(Collectors.toMap(MfcModelVo::getUid, Function.identity(), (k1, k2) -> k2));
            Set<Integer> uidSet = processMap.keySet();
            for (Integer uid : uidSet) {
                Process process = processMap.get(uid);
                if (!uidMap.containsKey(uid)) {
                    process.destroy();
                } else {
                    if (!process.isAlive()) {
                        processMap.remove(uid);
                        Iterator<MfcModelVo> it = likeOnlineModels.iterator();
                        while (it.hasNext()) {
                            MfcModelVo next = it.next();
                            if (uid.equals(next.getUid())) {
                                it.remove();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("",e);
        }
    }

    public static void addLikeModel(MfcModelVo mfcModelVo){
        List<ModelLikeList.ModelLike> models = modelLikeList.getModels();
        boolean b = models.stream().anyMatch(m -> m.getUid().equals(mfcModelVo.getUid()) || m.getNm().equals(mfcModelVo.getName()));
        if(!b){
            ModelLikeList.ModelLike modelLike = new ModelLikeList.ModelLike();
            modelLike.setUid(mfcModelVo.getUid());
            modelLike.setMode(1);
            modelLike.setNm(mfcModelVo.getName());
            models.add(modelLike);
        }
    }

    public static void removeLikeModel(MfcModelVo mfcModelVo){
        List<ModelLikeList.ModelLike> models = modelLikeList.getModels();
        models = models.stream().filter(m -> !(m.getUid().equals(mfcModelVo.getUid()))).collect(Collectors.toList());
        modelLikeList.setModels(models);
    }

}
