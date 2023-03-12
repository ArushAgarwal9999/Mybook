package com.example.Mybook.service;

import com.example.Mybook.model.Expert;
import com.example.Mybook.model.Response;
import com.example.Mybook.model.Task;
import com.example.Mybook.model.TaskDoneByExpert;
import com.example.Mybook.repository.ExpertRepository;
import com.example.Mybook.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.Mybook.utilities.CommonMethods.*;
import static com.example.Mybook.utilities.Constant.*;

@Service
public class ExpertService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ExpertRepository expertRepository;
    @Autowired
    TaskSchedulerService taskSchedulerService;
    public List<Task> getAllTask(String id){
        try{
            return taskRepository.getNewTask(getCurrentUTCTime(),WAITING_FOR_EXPERT_STATUS);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public List<Task> getAllRunningTask(String id){
        try{
            return taskRepository.getTaskOfExpert(id,getCurrentUTCTime(),RUNNING_STATUS);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public List<Task> getAllqueueTask(String id){
        try{
            return taskRepository.getTaskOfExpertQueue(id);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public boolean markTask(TaskDoneByExpert taskDone)
    {
        try{
            /*
                Assuming Expert will mark the task as one with in the hour limit of a specific task. It's completely the Expert responsibility to mark a task
                with in given hour and assuming expert will always able to complete
             */
                Task task = taskRepository.getSpecificTask(taskDone.getTaskId(), taskDone.getSubTaskId());
                task.setStatus(SUCCESS_STATUS);
                task.setExpEndTime(getCurrentTime());
                taskRepository.save(task);
                if(taskDone.getSubTaskId() != 4)
                {
                    Task nextTask = taskRepository.getSpecificTask(taskDone.getTaskId(), taskDone.getSubTaskId()+1);
                    nextTask.setStatus(RUNNING_STATUS);
                    nextTask.setTaskStartTime(getCurrentTime());
                    nextTask.setTaskEndTime(getEndTime(taskTimeMap.get(nextTask.getSubTaskId())));
                    taskRepository.save(nextTask);
                }
                Expert exp = expertRepository.getReferenceById(taskDone.getExpId());
                exp.setAvailable(true);
                exp.setCurrentHour(getTimeTakenByExpert(task.getExpStartTime(), getCurrentTime()));
                expertRepository.save(exp);
                taskSchedulerService.allocateNewTask();


                return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean takeTask(TaskDoneByExpert task)
    {
        try{
            Task nextTask = taskRepository.getSpecificTask(task.getTaskId(), task.getSubTaskId());
            nextTask.setStatus(RUNNING_STATUS);
            nextTask.setTaskStartTime(getCurrentTime());
            nextTask.setTaskEndTime(getEndTime(taskTimeMap.get(nextTask.getSubTaskId())));
            taskRepository.save(nextTask);
            for(Task t1: taskRepository.getAllTask(task.getTaskId()))
            {
                if(t1.getExpId() == null)
                {
                    t1.setExpId(task.getExpId());
                    taskRepository.save(t1);
                }
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


}
