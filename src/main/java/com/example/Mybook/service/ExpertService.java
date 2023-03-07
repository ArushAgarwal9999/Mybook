package com.example.Mybook.service;

import com.example.Mybook.model.Task;
import com.example.Mybook.model.TaskDoneByExpert;
import com.example.Mybook.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import static com.example.Mybook.utilities.CommonMethods.*;
import static com.example.Mybook.utilities.Constant.SUCCESS_STATUS;
import static com.example.Mybook.utilities.Constant.WAITING_FOR_EXPERT_STATUS;

@Service
public class ExpertService {
    @Autowired
    TaskRepository taskRepository;
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
                return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

}
