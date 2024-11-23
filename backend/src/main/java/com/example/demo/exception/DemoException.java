package com.example.demo.exception;


public class DemoException extends RuntimeException{
    public DemoException(String message){
        super(message);
    }

    public static DemoException notLogin(){
        return new DemoException("请先登录/注册!");
    }

    public static DemoException userNotExists() {
        return new DemoException("用户不存在!");
    }

    public static DemoException phoneAlreadyExists() {
        return new DemoException("该手机号已存在!");
    }

    public static DemoException phoneOrPasswordError() {
        return new DemoException("手机号或密码错误!");
    }

    public static DemoException mySolutionNotExists() {return new DemoException("我的方案是空的╥﹏╥...");}
}
