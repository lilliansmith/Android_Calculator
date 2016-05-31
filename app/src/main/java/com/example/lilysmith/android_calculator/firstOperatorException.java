package com.example.lilysmith.android_calculator;

/**
 * Created by LilySmith on 5/22/16.
 */
public class firstOperatorException extends Exception {

    public firstOperatorException() {
        super("Error: equation cannot start with an operator");
    }
}