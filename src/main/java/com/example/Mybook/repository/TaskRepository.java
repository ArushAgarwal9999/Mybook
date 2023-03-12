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
    @Query(value = "SELECT u FROM Task u WHERE (u.taskId=?1) and (u.subTaskId=?2)")
    Task getSpecificTask(long taskId, int subTaskId);
    @Query(value = "SELECT u FROM Task u WHERE u.taskEndTime <= ?1")
    List<Task> getAllFailedTask(Timestamp time);
    @Query(value = "SELECT u FROM Task u WHERE (u.expId = ?1 or u.expId IS NULL) and u.taskEndTime > ?2 and u.status = ?3 ORDER BY u.taskEndTime ASC")
    List<Task> getAllTaskOfExpert(String expId, Timestamp time, String status);
    @Query(value = "SELECT u FROM Task u WHERE (u.expId = ?1) and u.taskEndTime > ?2 and u.status = ?3 ORDER BY u.taskEndTime ASC")
    List<Task> getTaskOfExpert(String expId, Timestamp time, String status);
    @Query(value = "SELECT u FROM Task u WHERE (u.expId = ?1) and (u.status = 'Pending' or u.status = 'Waiting for Execution') ")
    List<Task> getTaskOfExpertQueue(String expId);
    @Query(value = "SELECT u FROM Task u WHERE (u.expId = ?1) and ( u.status = 'Waiting for Execution') ")
    List<Task> getTaskOfExpertWaiting(String expId);
    @Query(value = "SELECT u FROM Task u WHERE (u.expId IS NULL) and u.taskEndTime > ?1 and u.status = ?2 ORDER BY u.taskEndTime ASC")
    List<Task> getNewTask(Timestamp time, String status);
}

