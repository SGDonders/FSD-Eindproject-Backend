package com.fsdeindopdracht.execeptions;

public class HandleException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public HandleException() {
        super("An error occurred while processing the request.");
    }
}

