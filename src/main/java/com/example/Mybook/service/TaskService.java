package com.example.Mybook.service;

import com.example.Mybook.model.UserId;
import com.example.Mybook.model.Response;
import com.example.Mybook.model.Task;
import com.example.Mybook.model.User;
import com.example.Mybook.repository.TaskRepository;
import com.example.Mybook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import static com.example.Mybook.utilities.CommonMethods.*;
import static com.example.Mybook.utilities.Constant.*;

@Service
public class TaskService {
    private static long taskId = 101L;

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository repository;

    public Response createTask(UserId userId)
    {


        Response res = new Response();
        Optional<User> u = repository.findById(userId.getCusId());
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
            t1.setStatus(WAITING_FOR_EXPERT_STATUS);
            t1.setTaskName(TASK1);
            t1.setTaskStartTime(getCurrentTime());
            t1.setTaskEndTime(getEndTime(2));
            t1.setCusId(userId.getCusId());

            t2.setTaskId(task);
            t2.setSubTaskId(2);
            t2.setStatus(PENDING_STATUS);
            t2.setTaskName(TASK2);
            t2.setCusId(userId.getCusId());

            t3.setTaskId(task);
            t3.setSubTaskId(3);
            t3.setStatus(PENDING_STATUS);
            t3.setTaskName(TASK3);
            t2.setCusId(userId.getCusId());

            t4.setTaskId(task);
            t4.setSubTaskId(4);
            t4.setStatus(PENDING_STATUS);
            t4.setTaskName(TASK4);
            t4.setCusId(userId.getCusId());

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
        try{

            markExpTaskAsFailed();
            return taskRepository.getAllTaskOfCustomer(id);

        }
        catch (Exception e)
        {

            System.out.println("error in getAllTask");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public void markExpTaskAsFailed()
    {
        try{
            List<Task> failedTask = taskRepository.getAllFailedTask(getCurrentUTCTime());
            for(Task task : failedTask)
            {
                System.out.println(task);
                List<Task> failedSubTask = taskRepository.getAllTask(task.getTaskId());
                for(Task t: failedSubTask)
                {
                    if(t.getStatus().equals(PENDING_STATUS))
                        t.setStatus(FAILED_STATUS);
                }

                taskRepository.saveAll(failedTask);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
