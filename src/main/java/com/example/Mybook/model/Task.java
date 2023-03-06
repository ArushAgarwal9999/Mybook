package com.example.Mybook.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@ToString
@Table(name="task")

public class Task {
    @Id
    @GeneratedValue
    long id;
    @Getter
    @Setter
    String expId;
    @Getter
    @Setter
    String cusId;
    @Getter
    @Setter
    long taskId;
    @Getter
    @Setter
    int subTaskId;
    @Getter
    @Setter
    String taskName;
    @Getter
    @Setter
    String status;
    @Getter
    @Setter
    Timestamp taskStartTime;
    @Getter
    @Setter
    Timestamp taskEndTime;
    @Getter
    @Setter
    Timestamp expStartTime;
    @Getter
    @Setter
    Timestamp expEndTime;

}
