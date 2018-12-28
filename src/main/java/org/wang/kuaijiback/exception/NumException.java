package org.wang.kuaijiback.exception;

//值异常
public class NumException extends RuntimeException{

    public NumException(){
        super();
    }

    public NumException(String msg) {
        super(msg);
    }
}
