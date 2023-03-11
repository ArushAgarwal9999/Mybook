package com.example.Mybook.service;

import com.example.Mybook.model.Expert;
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
import static com.example.Mybook.utilities.Constant.SUCCESS_STATUS;
import static com.example.Mybook.utilities.Constant.WAITING_FOR_EXPERT_STATUS;

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
            return taskRepository.getAllTaskOfExpert(id,getCurrentUTCTime(),WAITING_FOR_EXPERT_STATUS);
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
                    nextTask.setStatus(WAITING_FOR_EXPERT_STATUS);
                    nextTask.setTaskStartTime(getCurrentTime());
                    nextTask.setTaskEndTime(getEndTime(2));
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
            return false;
        }
    }


}
