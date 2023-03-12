package com.example.Mybook.service;

import com.example.Mybook.model.Expert;
import com.example.Mybook.model.Response;
import com.example.Mybook.model.User;
import com.example.Mybook.repository.ExpertRepository;
import com.example.Mybook.repository.UserRepository;
import com.example.Mybook.utilities.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

import static com.example.Mybook.utilities.Constant.FAILED_STATUS;
import static com.example.Mybook.utilities.Constant.SUCCESS_STATUS;

@Service
public class UserService {
    @Autowired
    UserRepository repository;
    @Autowired
    ExpertRepository expertRepository;
    @Autowired
    TaskSchedulerService taskSchedulerService;
    public Response loginUser(User user)
    {
        Response res = new Response();
        try
        {
            Optional<User> u = repository.findById(user.getId());
            if(u.isEmpty())
            {
                repository.save(user);
                if(user.getType().equals(Constant.EXPERT))
                {
                    Expert exp = new Expert();
                    exp.setExpId(user.getId());
                    exp.setStartDate(Date.valueOf("1980-01-01"));
                    exp.setAvailable(true);
                    expertRepository.save(exp);


                }
                res.setStatus(SUCCESS_STATUS);
                res.setMsg("New User add Successfully ");
            }
            else{
                User tmp = u.get();
                System.out.println(tmp);
                if(!tmp.getType().equals(user.getType()))
                {
                    res.setStatus(FAILED_STATUS);
                    res.setMsg("User already exist with different Type ");
                }
                else{
                    res.setStatus(SUCCESS_STATUS);
                    res.setMsg("User login Successfully ");
                }
            }
            if(user.getType().equals(Constant.EXPERT))
            {
                taskSchedulerService.assignTask();
            }

        }
        catch (Exception e)
        {
            res.setStatus(FAILED_STATUS);
            res.setMsg("error in loginUser");
            System.out.println("error in loginUser");
            e.printStackTrace();
        }
        return res;
    }

}
