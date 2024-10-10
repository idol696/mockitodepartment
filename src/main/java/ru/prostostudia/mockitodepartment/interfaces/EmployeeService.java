package ru.prostostudia.mockitodepartment.interfaces;

import ru.prostostudia.mockitodepartment.Employee;

import java.util.Map;

public interface EmployeeService {
    Map<String, Employee> getEmployees();
    Employee addEmployee(String firstName, String lastName);
    void addEmployee(String firstName, String lastName, int department, double salary);
    Employee deleteEmployee(String firstName, String lastName);
    Employee findEmployee(String firstName, String lastName);
    Map<String,String> errorResponse(String errorMessage);
    void demoFill();
}
