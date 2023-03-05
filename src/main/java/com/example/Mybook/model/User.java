package com.example.Mybook.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "users")
public class User {

    @Id
    @Getter
    @Setter
    String id;
    @Getter
    @Setter
    String type;
}
