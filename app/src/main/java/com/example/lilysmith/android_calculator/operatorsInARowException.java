package com.example.lilysmith.android_calculator;

/**
 * Created by LilySmith on 5/22/16.
 */
public class operatorsInARowException extends Exception {

    public operatorsInARowException() {
        super ("Error: cannot have two operators in a row");
    }
}
