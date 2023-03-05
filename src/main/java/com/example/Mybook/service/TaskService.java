package com.example.Mybook.service;

import com.example.Mybook.model.Customer;
import com.example.Mybook.model.Response;
import com.example.Mybook.model.Task;
import com.example.Mybook.model.User;
import com.example.Mybook.repository.TaskRepository;
import com.example.Mybook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

import static com.example.Mybook.utilities.Constant.*;

@Service
public class TaskService {
    private static long taskId = 101L;

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository repository;

    public Response createTask(Customer customer)
    {


        Response res = new Response();
        Optional<User> u = repository.findById(customer.getCusId());
        if(u.isEmpty())
        {
            res.setStatus(FAILED_STATUS);
            res.setMsg("Invalid Customer ID");
            return res;
        }
        try{
            long task = taskId++;
           Task t1 = new Task();
            Task t2 = new Task();
            Task t3 = new Task();
            Task t4 = new Task();

            t1.setTaskId(task);
            t1.setSubTaskId(1);
            t1.setStats(WAITING_FOR_EXPERT_STATUS);
            t1.setTaskName(TASK1);
            java.sql.Timestamp startTime = new Timestamp(System.currentTimeMillis());
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, +2);
            java.sql.Timestamp endTime = new Timestamp(cal.getTimeInMillis());
            t1.setTaskStartTime(startTime);
            t1.setTaskEndTime(endTime);
            t1.setCusId(customer.getCusId());

            t2.setTaskId(task);
            t2.setSubTaskId(2);
            t2.setStats(PENDING_STATUS);
            t2.setTaskName(TASK2);
            t2.setCusId(customer.getCusId());

            t3.setTaskId(task);
            t3.setSubTaskId(3);
            t3.setStats(PENDING_STATUS);
            t3.setTaskName(TASK3);
            t2.setCusId(customer.getCusId());

            t4.setTaskId(task);
            t4.setSubTaskId(4);
            t4.setStats(PENDING_STATUS);
            t4.setTaskName(TASK4);
            t4.setCusId(customer.getCusId());

            taskRepository.save(t1);
            taskRepository.save(t2);
            taskRepository.save(t3);
            taskRepository.save(t4);

            res.setStatus(SUCCESS_STATUS);
            res.setMsg("Task create successfully");
        }
        catch (Exception e)
        {
            res.setStatus(FAILED_STATUS);
            res.setMsg("error in create task");
            System.out.println("error in createTask");
            e.printStackTrace();
        }
        return res;
    }

    public List<Task> getAllTask(String id)
    {
        Response res = new Response();

        try{
            return taskRepository.getAllTask(id);

        }
        catch (Exception e)
        {

            System.out.println("error in getAllTask");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}