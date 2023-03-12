package com.example.Mybook.service;

import com.example.Mybook.model.Expert;
import com.example.Mybook.model.Task;
import com.example.Mybook.repository.ExpertRepository;
import com.example.Mybook.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.Mybook.utilities.CommonMethods.*;
import static com.example.Mybook.utilities.Constant.*;

@Service
public class TaskSchedulerService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ExpertRepository expertRepository;

    public synchronized void allocateNewTask(Expert exp)
    {

        try {

            List<Task>  newTask = taskRepository.getNewTask(getCurrentUTCTime(),WAITING_FOR_EXPERT_STATUS);
            System.out.println("newTask-->>>"+newTask);
            for(Task t:newTask)
            {
                if(canAssignTask(t, exp))
                {
                    assignTask(t,exp);
                    return;
                }
            }






        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




    }
    public synchronized  void allocateNextTask(Expert exp)
    {
            List<Task> task = taskRepository.getTaskOfExpertWaiting(exp.getExpId());
            if(task.isEmpty())
            {
                allocateNewTask(exp);
            }
            else{
                for(Task t:task)
                {
                    if(canAssignTask(t, exp))
                    {
                        assignTask(t,exp);
                        return;
                    }

                }
            }
    }
    public synchronized void markNextTask(long prevTaskId, int prevSubTaskId )
    {

        Task task = taskRepository.getSpecificTask(prevTaskId, prevSubTaskId+1);
        if(task != null)
        {
            task.setStatus(WAITING_FOR_EXECUTION_STATUS);
            task.setTaskStartTime(getCurrentTime());
            task.setTaskEndTime(getEndTime(taskTimeMap.get(prevSubTaskId+1)));
            taskRepository.save(task);
        }

    }
    public synchronized boolean canAssignTask(Task task, Expert expert)
    {
        try{
            if(task.getExpId() != null && !task.getExpId().equals(expert.getExpId()))
                return false;
            if(expert.getStartDate().toString().equals(getCurrentDate().toString()))
            {
                if(expert.getCurrentHour() >= expertMaxHour)
                    return false;
                int expertRemainHour = expertMaxHour - expert.getCurrentHour();
                return expertRemainHour >= taskTimeMap.get(task.getSubTaskId());
            }

            return true;

        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public synchronized void assignTask()
    {
        try{
            List<Expert> freeExpert = expertRepository.getAllFreeExpert();
            for(Expert exp :freeExpert)
            {
                allocateNextTask(exp);
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }

    }
    public synchronized void assignTask(Task task, Expert expert)
    {
        try {
            System.out.println("task -->>"+task);
            System.out.println("expert -->>"+expert);
            if(!expert.getStartDate().toString().equals(getCurrentDate().toString()))
            {
                expert.setStartDate(getCurrentDate());
            }
            expert.setTaskId(task.getTaskId());
            expert.setAvailable(false);
            expert.setCurrTask(task.getSubTaskId());
            task.setExpStartTime(getCurrentTime());
            task.setStatus(RUNNING_STATUS);
            task.setExpId(expert.getExpId());
            expertRepository.save(expert);
            taskRepository.save(task);
            for(Task t1: taskRepository.getAllTask(task.getTaskId()))
            {
                if(t1.getExpId() == null)
                {
                    t1.setExpId(expert.getExpId());
                    taskRepository.save(t1);
                }
            }
            System.out.println("task after assign -->>"+task);
            System.out.println("expert after  -->>"+expert);
            System.out.println("all running taks "+taskRepository.getTaskOfExpert(expert.getExpId(),getCurrentUTCTime(),RUNNING_STATUS));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
