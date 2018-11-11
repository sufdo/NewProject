package com.example.user.project;

public class Validation {
    public boolean isEmpty(String value1,String value2,String value3,String value4,String value5)
    {
        if((value1.isEmpty())||(value2.isEmpty())||(value3.isEmpty())||(value4.isEmpty())||(value5.isEmpty()))
        {
            return true;
        }
        return false;
    }
}
