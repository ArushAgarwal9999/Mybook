package com.example.Mybook.service;

import com.example.Mybook.model.Expert;
import com.example.Mybook.model.Task;
import com.example.Mybook.model.TaskDoneByExpert;
import com.example.Mybook.repository.ExpertRepository;
import com.example.Mybook.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                Expert exp = expertRepository.getReferenceById(taskDone.getExpId());

                exp.setCurrentHour(getTimeTakenByExpert(task.getExpStartTime(), getCurrentTime()));
                expertRepository.save(exp);
                taskSchedulerService.markNextTask(taskDone.getTaskId(), taskDone.getSubTaskId());
                if(isExpAvailable(taskDone.getExpId()))
                {
                    exp.setAvailable(true);
                    expertRepository.save(exp);
                    taskSchedulerService.assignTask();
                }


                return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isExpAvailable(String expId)
    {
        System.out.println("task -->>"+taskRepository.getTaskOfExpert(expId,getCurrentUTCTime(),RUNNING_STATUS));
        return taskRepository.getTaskOfExpert(expId, getCurrentUTCTime(), RUNNING_STATUS).isEmpty();
    }
    public boolean takeTask(TaskDoneByExpert task)
    {
        try{
            Task nextTask = taskRepository.getSpecificTask(task.getTaskId(), task.getSubTaskId());
            Expert exp = expertRepository.getReferenceById(task.getExpId());
            if(nextTask.getExpId() == null && taskSchedulerService.canAssignTask(nextTask,exp))
            {
                taskSchedulerService.assignTask(nextTask, exp);
                return true;
            }
            else{
                return false;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


}
