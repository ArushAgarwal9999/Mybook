package com.example.Mybook.service;

import com.example.Mybook.model.Expert;
import com.example.Mybook.model.Task;
import com.example.Mybook.repository.ExpertRepository;
import com.example.Mybook.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

import static com.example.Mybook.utilities.CommonMethods.*;
import static com.example.Mybook.utilities.Constant.*;

@Service
public class TaskSchedulerService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ExpertRepository expertRepository;

    public synchronized void allocateNewTask()
    {

        try {

            List<Task>  newTask = taskRepository.getNewTask(getCurrentUTCTime(),WAITING_FOR_EXPERT_STATUS);
            System.out.println("newTask-->>>"+newTask);
            List<Expert> freeExpert = expertRepository.getAllFreeExpert();
            int i = 0;
            int j = 0;
            while(i<newTask.size() && j<freeExpert.size())
            {
                if(canAssignTask(newTask.get(i), freeExpert.get(j)))
                {
                    assignTask(newTask.get(i), freeExpert.get(j) );
                    i++;
                    j++;
                }
                else{
                    j++;
                }

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




    }
    private boolean canAssignTask(Task task, Expert expert)
    {
        try{

            if(expert.getStartDate().toString().equals(getCurrentDate().toString()))
            {
                int expertRemainHour = expertMaxHour - expert.getCurrentHour();
                if(expertRemainHour< taskTimeMap.get(task.getSubTaskId()) )
                {
                    return false;
                }
            }

            return true;

        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    private void assignTask(Task task, Expert expert)
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

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
