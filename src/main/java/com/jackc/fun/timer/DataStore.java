package com.jackc.fun.timer;

import com.jackc.fun.pojo.Task;

import java.util.*;
import java.util.concurrent.Future;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/6/28 17:44
 */
public class DataStore {
    public  static final PriorityQueue<Task> taskQueue = new PriorityQueue((Comparator<Task>) (o1, o2) -> o1.getExecuteTime().compareTo(o2.getExecuteTime()));

    public static final Set storeRead = new HashSet();

    public static final List<String> mfcUrl = new ArrayList<>();

    public static final List<Future> taskFutures = new ArrayList<>();

    public static final Map<String,Future> taskFuturesMap = new HashMap<>();

    public static final String mfcFuturesName = "mfcFuturesName";

    public static  Integer taskCount = 36;

    static {
        mfcUrl.add("https://mfcp.herokuapp.com/tools/");
    }

    public static void addTaskQueue(Task task){
        taskQueue.offer(task);
    }

    public static void main(String[] args) {


    }
}
