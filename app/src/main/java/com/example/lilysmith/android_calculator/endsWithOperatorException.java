package com.example.lilysmith.android_calculator;

/**
 * Created by LilySmith on 5/22/16.
 */
public class endsWithOperatorException extends Exception {

    public endsWithOperatorException() {
        super("Error: equation cannot end with an operator");
    }
}
