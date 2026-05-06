package com.chirag.teamtaskmanager.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String message) {
        super(message);
    }
}