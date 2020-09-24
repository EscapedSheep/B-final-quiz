package com.example.demo.exception;

import static com.example.demo.exception.ErrorMessage.GROUP_NAME_EXISTED;

public class GroupNameExistedException extends RuntimeException{
    public GroupNameExistedException() {
        super(GROUP_NAME_EXISTED);
    }
}
