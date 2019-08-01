package com.jackc.fun.timer;

import com.jackc.fun.pojo.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.PriorityQueue;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/6/28 17:29
 */
@Service
@Slf4j
public class MfcTimer {
    @Autowired
    private RestTemplate restTemplate;

//    @Scheduled(cron="0 */1 * * * ?")
    public void  mfc(){
        PriorityQueue<Task> taskQueue = DataStore.taskQueue;
        long time =System.currentTimeMillis();
        while (!taskQueue.isEmpty()){
            Task task = taskQueue.peek();
            if(task == null) {
                break;
            }
            Long executeTime = task.getExecuteTime();
            if(executeTime.compareTo(time) < 0){
                taskQueue.poll();
                Integer count = task.getCount();
                Integer runCount = task.getRunCount();
                Integer extensionTime = task.getExtensionTime();
                if(count != null && runCount != null && extensionTime != null && runCount < count){
                    task.setRunCount(++runCount);
                    task.setExecuteTime(time+task.getExtensionTime()*60*1000);
                    DataStore.addTaskQueue(task);
                }
                handleTask(task);
            }else {
                break;
            }
        }
    }

    private void handleTask(Task task) {
        Integer type = task.getType();
        if(type == 1){
            String url = task.getContent();
            String content = null;
            try {
                content = restTemplate.getForObject(url, String.class);
            } catch (RestClientException e) {
                content = restTemplate.getForObject(url, String.class);
            }
            log.info("返回内容{}",content);
        }
    }



//    @Override
//    public void run(String... args) throws Exception {
//        Task task = new Task();
//        task.setId(1);
//        task.setType(1);
//        task.setContent("https://mfcp511.herokuapp.com/tools/");
//        task.setExecuteTime(System.currentTimeMillis());
//        task.setRunCount(0);
//        task.setCount(3);
//        task.setExtensionTime(2);
//        DataStore.addTaskQueue(task);
//        Integer runCount = task.getRunCount();
//        task.setRunCount(++runCount);
//
//        Task task2 = new Task();
//        task2.setId(2);
//        task2.setType(1);
//        task2.setContent("https://mfcp511.herokuapp.com/tools/");
//        task2.setExecuteTime(DateUtil.add(new Date(),12,2).getTime());
//        task2.setRunCount(0);
//        task2.setCount(3);
//        task2.setExtensionTime(2);
//        DataStore.addTaskQueue(task2);
//
//        Task task3 = new Task();
//        task3.setId(3);
//        task3.setType(1);
//        task3.setContent("https://mfcp511.herokuapp.com/tools/");
//        task3.setExecuteTime(DateUtil.getAfterDay(new Date(),2).getTime());
//        task3.setRunCount(0);
//        task3.setCount(3);
//        task3.setExtensionTime(2);
//        DataStore.addTaskQueue(task3);
//
//        Task task4 = new Task();
//        task4.setId(4);
//        task4.setType(1);
//        task4.setContent("https://mfcp511.herokuapp.com/tools/");
//        task4.setExecuteTime(DateUtil.getAfterDay(new Date(),-4).getTime());
//        task4.setRunCount(0);
//        task4.setCount(3);
//        task4.setExtensionTime(2);
//        DataStore.addTaskQueue(task4);
//    }
}
