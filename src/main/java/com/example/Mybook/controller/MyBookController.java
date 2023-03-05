package com.example.Mybook.controller;

import com.example.Mybook.model.UserId;
import com.example.Mybook.model.Response;
import com.example.Mybook.model.User;
import com.example.Mybook.service.ExpertService;
import com.example.Mybook.service.TaskService;
import com.example.Mybook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static com.example.Mybook.utilities.Constant.FAILED_STATUS;
import static com.example.Mybook.utilities.Constant.SUCCESS_STATUS;

@RestController
public class MyBookController {
    @Autowired
    UserService userService;
    @Autowired
    TaskService taskService;
    @Autowired
    ExpertService expertService;


    @PostMapping("/mybook/login")
    public Response loginUser(@RequestBody User user)
    {
        try{
            return userService.loginUser(user);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Response res = new Response();
            res.setStatus(FAILED_STATUS);
            res.setMsg("Error Occurred while processing the request");
            return res;
        }
    }
    @PostMapping("/mybook/createTask")
    public Response createTask(@RequestBody UserId userId)
    {
        try{
            return taskService.createTask(userId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Response res = new Response();
            res.setStatus(FAILED_STATUS);
            res.setMsg("Error Occurred while processing the request");
            return res;
        }
    }
    @GetMapping("/mybook/getAllCustomerTask/{id}")
    public Response getAllCustomerTask(@PathVariable("id") String id)
    {
        Response res = new Response();
        res.setStatus(SUCCESS_STATUS);
        try {
            res.setResult(Collections.singletonList(taskService.getAllTask(id)));
            return res;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return res;
        }
    }
    @GetMapping("/mybook/getAllExpertTask/{id}")
    public Response getAllExpertTask(@PathVariable("id") String id)
    {
        Response res = new Response();
        res.setStatus(SUCCESS_STATUS);
        try {
            res.setResult(Collections.singletonList(expertService.getAllTask(id)));
            return res;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return res;
        }
    }

}
