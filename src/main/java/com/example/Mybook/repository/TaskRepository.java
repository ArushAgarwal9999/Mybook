package com.example.Mybook.repository;
import com.example.Mybook.model.Task;
import com.example.Mybook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "SELECT u FROM Task u WHERE u.cusId=?1")
    List<Task> getAllTaskOfCustomer(String cusId);
    @Query(value = "SELECT u FROM Task u WHERE u.taskId=?1")
    List<Task> getAllTask(long taskId);
    @Query(value = "SELECT u FROM Task u WHERE u.expEndTime <= ?1")
    List<Task> getAllFailedTask(Timestamp time);
    @Query(value = "SELECT u FROM Task u WHERE (u.expId = ?1 or u.expId IS NULL) and u.expEndTime > ?2 and u.status = ?3 ORDER BY u.expEndTime ASC")
    List<Task> getAllTaskOfExpert(String expId, Timestamp time, String status);
}

