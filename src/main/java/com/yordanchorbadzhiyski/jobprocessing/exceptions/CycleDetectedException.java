package com.yordanchorbadzhiyski.jobprocessing.exceptions;

public class CycleDetectedException extends Throwable{
    public CycleDetectedException(String message){
        super(message);
    }
}
