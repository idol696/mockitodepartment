package ru.prostostudia.mockitodepartment.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.prostostudia.mockitodepartment.interfaces.DepartmentService;
import ru.prostostudia.mockitodepartment.Employee;
import ru.prostostudia.mockitodepartment.interfaces.EmployeeService;
import ru.prostostudia.mockitodepartment.exceptions.*;

import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public Map<String, Employee> employeeList() {
        employeeService.demoFill();
        return employeeService.getEmployees();
    }

    @GetMapping(path = "/del")
    public ResponseEntity<?> employeeDelete(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        try {
            Employee employee = employeeService.deleteEmployee(firstName, lastName);
            return ResponseEntity.ok(employee);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(employeeService.errorResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "/add")
    public ResponseEntity<?> employeeAdd(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        try {
            return ResponseEntity.ok(employeeService.addEmployee(firstName, lastName));
        } catch (EmployeeAlreadyAddedException | EmployeeStorageIsFullException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(employeeService.errorResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "/find")
    public ResponseEntity<?> employeeFind(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        try {
            return ResponseEntity.ok(employeeService.findEmployee(firstName, lastName));
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(employeeService.errorResponse(e.getMessage()));
        }
    }
}

