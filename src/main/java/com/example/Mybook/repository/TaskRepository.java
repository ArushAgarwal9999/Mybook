package com.example.Mybook.repository;
import com.example.Mybook.model.Task;
import com.example.Mybook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "SELECT u FROM Task u WHERE u.cusId=?1")
    List<Task> getAllTask(String id);
}

