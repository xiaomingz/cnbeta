package com.ming.cnbeta.bean;

/**
 * Created by ming on 16/2/19.
 */
public class ResponseObject<T> {

    private String state;

    private String message;

    private T result;

    public Boolean isOK(){
        return state.equals("success");
    }

    public void setState(String state){
        this.state = state;
    }
    public String getState(){
        return this.state;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setResult(T result){
        this.result = result;
    }
    public T getResult(){
        return this.result;
    }
}
