package com.example.lilysmith.android_calculator;

/**
 * Created by LilySmith on 5/22/16.
 */
public class divideByZeroException extends Exception {

    public divideByZeroException(){
        super("Error: cannot divide by zero");
    }
}
