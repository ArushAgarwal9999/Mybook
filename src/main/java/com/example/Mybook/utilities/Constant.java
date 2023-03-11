package com.example.Mybook.utilities;

import java.util.HashMap;

public class Constant {
    public static final String EXPERT = "expert";
    public static final String PENDING_STATUS = "Pending";
    public static final String WAITING_FOR_EXPERT_STATUS = "Waiting for Expert";
    public static final String WAITING_FOR_EXECUTION_STATUS = "Waiting for Execution";
    public static final String RUNNING_STATUS = "Running";
    public static final String SUCCESS_STATUS = "Successfully";
    public static final String FAILED_STATUS = "Failed";
    public static final String TASK1 = "Review Sales";
    public static final String TASK2 = "Review Purchases";
    public static final String TASK3 = "Compute Tax";
    public static final String TASK4 = "Add Tax to Books";


    public static final String CUSTOMER = "customer";
    public static final int expertMaxHour = 8;
    public static final HashMap<Integer,Integer> taskTimeMap = new HashMap<>();
    static{
        taskTimeMap.put(1,2);
        taskTimeMap.put(2,2);
        taskTimeMap.put(3,3);
        taskTimeMap.put(4,1);
    }
}
