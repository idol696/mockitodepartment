package ru.prostostudia.mockitodepartment.exceptions;

public class EmployeeStorageIsFullException extends RuntimeException {
    public EmployeeStorageIsFullException() {
        super("ArrayIsFull");
    }
}
