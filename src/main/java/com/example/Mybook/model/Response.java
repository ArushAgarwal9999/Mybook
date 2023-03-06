package com.example.Mybook.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public class Response {

    @Getter
    @Setter
    String msg;
    @Getter
    @Setter
    String status;
    @Getter
    @Setter
    List<Object> result;
}
