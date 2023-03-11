package com.example.Mybook.repository;

import com.example.Mybook.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExpertRepository extends JpaRepository<Expert, String> {
    @Query(value = "select u from Expert u where u.isAvailable = true ORDER BY u.startDate DESC")
    List<Expert> getAllFreeExpert();
}
