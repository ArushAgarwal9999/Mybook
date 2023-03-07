package com.example.Mybook.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class TaskDoneByExpert {
    @Getter
    @Setter
    long taskId;
    @Getter
    @Setter
    int subTaskId;
}
