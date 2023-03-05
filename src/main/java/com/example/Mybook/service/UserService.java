package com.example.Mybook.service;

import com.example.Mybook.model.Responce;
import com.example.Mybook.model.User;
import com.example.Mybook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository repository;
    public Responce loginUser(User user)
    {
        Responce res = new Responce();
        try
        {
            Optional<User> u = repository.findById(user.getId());
            if(u.isEmpty())
            {
                repository.save(user);
                res.setStatusCode(200);
                res.setMsg("New User add Successfully ");
            }
            else{
                User tmp = u.get();
                if(!tmp.getType().equals(user.getType()))
                {
                    res.setStatusCode(400);
                    res.setMsg("User already exist with different Type ");
                }
                else{
                    res.setStatusCode(200);
                    res.setMsg("User login Successfully ");
                }
            }

        }
        catch (Exception e)
        {
            res.setStatusCode(400);
            res.setMsg("error in loginUser");
            System.out.println("error in loginUser");
            e.printStackTrace();
        }
        return res;
    }
}
