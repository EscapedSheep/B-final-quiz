package com.example.demo.exception;

import static com.example.demo.exception.ErrorMessage.GROUP_FAILED;

public class GroupFailedException extends RuntimeException{
    public GroupFailedException() {
        super(GROUP_FAILED);
    }
}
