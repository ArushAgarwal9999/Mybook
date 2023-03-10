package com.example.Mybook.repository;


import com.example.Mybook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "select * from users u where u.type = 'expert'",nativeQuery = true)
    Collection<User> findAllExpert();
}