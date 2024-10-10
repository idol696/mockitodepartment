package ru.prostostudia.mockitodepartment.exceptions;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException() {
        super("EmployeeNotFound");
    }
}
