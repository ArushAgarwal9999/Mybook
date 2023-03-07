package com.example.Mybook.service;

import com.example.Mybook.model.Task;
import com.example.Mybook.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static com.example.Mybook.utilities.CommonMethods.getCurrentUTCTime;
import static com.example.Mybook.utilities.Constant.WAITING_FOR_EXPERT_STATUS;

public class TaskSchedulerService {
    @Autowired
    TaskRepository taskRepository;
    public void allocateTask(String expId)
    {


        List<Task>  taskOfExpert = taskRepository.getTaskOfExpert(expId,getCurrentUTCTime(),WAITING_FOR_EXPERT_STATUS);
        List<Task>  newTask = taskRepository.getNewTask(getCurrentUTCTime(),WAITING_FOR_EXPERT_STATUS);
        System.out.println("taskOfExpert-->>>"+taskOfExpert);
        System.out.println("newTask-->>>"+newTask);

    }
}
