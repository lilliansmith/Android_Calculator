package com.example.lilysmith.android_calculator;

/**
 * Created by LilySmith on 5/22/16.
 */
public class badNegativeSignInput extends Exception
{
    public badNegativeSignInput()
    {
        super ("error: improper use of +/- symbol");
    }
}