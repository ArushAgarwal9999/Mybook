package com.example.Mybook.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Entity
@ToString
@Table(name="expert")
public class Expert {
    @Id
    @Getter
    @Setter
    String expId;
    @Getter
    @Setter
    Date statTime;
    @Getter
    @Setter
    int currentHour;
    @Getter
    @Setter
    boolean isAvailable;
    @Getter
    @Setter
    long taskId;




}
