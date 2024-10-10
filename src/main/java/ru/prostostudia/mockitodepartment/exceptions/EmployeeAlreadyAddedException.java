package ru.prostostudia.mockitodepartment.exceptions;

public class EmployeeAlreadyAddedException extends RuntimeException {
    public EmployeeAlreadyAddedException() {
        super("EmployeeAlreadyAdded");
    }
}