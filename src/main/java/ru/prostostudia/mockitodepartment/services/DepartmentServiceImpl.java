package ru.prostostudia.mockitodepartment.services;

import org.springframework.stereotype.Service;
import ru.prostostudia.mockitodepartment.interfaces.DepartmentService;
import ru.prostostudia.mockitodepartment.Employee;
import ru.prostostudia.mockitodepartment.interfaces.EmployeeService;
import ru.prostostudia.mockitodepartment.exceptions.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    private List<Employee> getEmployeesDepartment(int department) {
        return employeeService.getEmployees().values().stream()
                .filter(e -> e.getDepartment() == department || department == ALL_DEPARTMENTS)
                .toList();
    }


    @Override
    public Map<String, Employee> getEmployeesInDepartment(int department) {
        if (getEmployeesDepartment(department).isEmpty()) throw new EmployeeNotFoundException();
        return employeeService.getEmployees().entrySet().stream()
                .filter(e -> e.getValue().getDepartment() == department || department == ALL_DEPARTMENTS)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Employee getSalaryMin(int department) {
        if (getEmployeesDepartment(department).isEmpty()) throw new EmployeeNotFoundException();
        Optional<Employee> employeeMin = getEmployeesDepartment(department).stream()
                .min(Comparator.comparingDouble(Employee::getSalary));
        if (employeeMin.isPresent()) return employeeMin.get();
        throw new EmployeeNotFoundException();
    }

    @Override
    public Employee getSalaryMax(int department) {
        if (getEmployeesDepartment(department).isEmpty()) throw new EmployeeNotFoundException();
        Optional<Employee> employeeMax = getEmployeesDepartment(department).stream()
                .max(Comparator.comparingDouble(Employee::getSalary));
        if (employeeMax.isPresent()) return employeeMax.get();
        throw new EmployeeNotFoundException();
    }

    @Override
    public Map<Integer, List<Employee>> getEmployeesGroupByDepartment() {
        return employeeService.getEmployees().values().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    @Override
    public Map<String,Double> getSalarySum(int department) {
        if (getEmployeesDepartment(department).isEmpty()) throw new EmployeeNotFoundException();
        Map<String,Double> result = new HashMap<>();
        result.put("salarySum",getEmployeesDepartment(department).stream()
                .mapToDouble(Employee::getSalary).sum() );
        return result;
    }

    @Override
    public Map<String,String> errorResponse(String errorMessage) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", errorMessage);
        return errorResponse;
    }
}