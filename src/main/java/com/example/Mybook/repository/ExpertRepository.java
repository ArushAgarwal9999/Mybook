package com.example.Mybook.repository;

import com.example.Mybook.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExpertRepository extends JpaRepository<Expert, String> {
}
