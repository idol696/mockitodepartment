package ru.prostostudia.mockitodepartment.interfaces;

import ru.prostostudia.mockitodepartment.Employee;

import java.util.List;
import java.util.Map;

public interface DepartmentService
{
    int ALL_DEPARTMENTS = -1;
    Map<String, Employee> getEmployeesInDepartment(int department);
    Employee getSalaryMin(int department);
    Employee getSalaryMax(int department);
    Map<String,Double> getSalarySum(int department);
    Map<Integer, List<Employee>> getEmployeesGroupByDepartment();
    Map<String,String> errorResponse(String errorMessage);
}
