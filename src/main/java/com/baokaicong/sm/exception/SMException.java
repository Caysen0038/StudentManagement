package com.baokaicong.sm.exception;

public class SMException extends RuntimeException {

    private String code;

    public SMException(){
        super();
        this.code="500";
    }

    public SMException(String code){
        super();
        this.code=code;
    }

    public SMException(String msg,String code){
        super(msg);
        this.code=code;
    }

    public SMException(Throwable t,String code){
        super(t);
        this.code=code;
    }

    public SMException(String msg,Throwable t,String code){
        super(msg,t);
        this.code=code;
    }

    public void setCode(String code){
        this.code=code;
        this.code=code;
    }

    public String getCode(){
        return code;
    }
}
