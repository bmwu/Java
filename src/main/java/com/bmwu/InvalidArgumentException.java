package com.bmwu;

/**
 * Created by michael on 3/18/17.
 */
public class InvalidArgumentException extends Exception {

    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException(Throwable error) {
        super(error);
    }
}
