package com.example.Mybook.service;

import com.example.Mybook.model.Task;
import com.example.Mybook.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.example.Mybook.utilities.Constant.WAITING_FOR_EXPERT_STATUS;

public class ExpertService {
    @Autowired
    TaskRepository taskRepository;
    public List<Task> getAllTask(String id){
        try{
            return taskRepository.getAllTaskOfExpert(id,new Timestamp(System.currentTimeMillis()),WAITING_FOR_EXPERT_STATUS);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
