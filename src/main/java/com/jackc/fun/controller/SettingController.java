package com.jackc.fun.controller;

import com.jackc.fun.timer.DataStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/7/8 15:42
 */
@RestController
@RequestMapping("/setting")
public class SettingController {


    @GetMapping("/addMfcUrl")
    public Object addMfcUrl(@RequestParam String url){
        DataStore.mfcUrl.add(url);
        return DataStore.mfcUrl;
    }

    @GetMapping("/removeMfcUrl")
    public Object removeMfcUrl(){
        DataStore.mfcUrl.clear();
        return DataStore.mfcUrl;
    }

    @GetMapping("/cleanTask")
    public Object cleanTask(){
        DataStore.taskQueue.clear();
        return DataStore.taskQueue;
    }

    @GetMapping("/getTask")
    public Object getTask(){
        return DataStore.taskQueue;
    }

    @GetMapping("/taskCount")
    public Object taskCount(@RequestParam Integer taskCount){
        DataStore.taskCount = taskCount;
        return DataStore.taskCount;
    }

}
