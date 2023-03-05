package com.example.Mybook.controller;

import com.example.Mybook.model.Responce;
import com.example.Mybook.model.User;
import com.example.Mybook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping("/mybook/login")
    public Responce loginUser(@RequestBody User user)
    {
        try{
            return userService.loginUser(user);
        }
        catch (Exception r)
        {
            Responce res = new Responce();
            res.setStatusCode(500);
            res.setMsg("Error Occurred while processing the request");
            return res;
        }
    }

}
