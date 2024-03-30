package com.example.todoapp.dto;

import lombok.Getter;

@Getter
public class Result<T> {
    private final T data;
    private final boolean succeeded;
    private final  String message;
    private Result(T data, boolean succeeded,String message){
        this.succeeded = succeeded;
        this.data = data;
        this.message = message;
    }
    public  static <T>  Result<T> Success(T data,String message){
        return  new Result<>(data,true,message);
    }
    public  static <T>  Result<T> Fail(String message){
        return  new Result<>(null,false,message);
    }
}
