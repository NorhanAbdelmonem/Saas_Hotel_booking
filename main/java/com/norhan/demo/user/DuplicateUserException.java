package com.norhan.demo.user;

import com.norhan.demo.common.DuplicateResourceException;

public class DuplicateUserException extends DuplicateResourceException
{
    public DuplicateUserException(String email){
        super("this email: "+email+" is already exist ");

    }

}
