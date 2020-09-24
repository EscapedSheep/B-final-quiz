package com.example.demo.exception;

import static com.example.demo.exception.ErrorMessage.ID_NOT_EXISTED;

public class IdNotExistedException extends RuntimeException{
    public IdNotExistedException() {
        super(ID_NOT_EXISTED);
    }
}
