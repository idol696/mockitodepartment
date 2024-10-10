package ru.prostostudia.mockitodepartment.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.prostostudia.mockitodepartment.interfaces.DepartmentService;
import ru.prostostudia.mockitodepartment.interfaces.EmployeeService;
import ru.prostostudia.mockitodepartment.exceptions.*;

import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;


    public DepartmentController(EmployeeService employeeService, DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<?> employeeList() {
        return ResponseEntity.ok(departmentService.getEmployeesGroupByDepartment());
    }

    @GetMapping(path = "/{id}/employees")
    public ResponseEntity<?> employeeInDepartment(@PathVariable("id") int department) {
        try {
            return ResponseEntity.ok(departmentService.getEmployeesInDepartment(department));
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(departmentService.errorResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "/employees")
    public ResponseEntity<?> employeeGroupByDepartment() {
        return ResponseEntity.ok(departmentService.getEmployeesGroupByDepartment());
    }

    @GetMapping(path = "/{id}/salary/min")
    public ResponseEntity<?> employeeMinSalary(@PathVariable("id") int department) {
        try {
            return ResponseEntity.ok(departmentService.getSalaryMin(department));
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(departmentService.errorResponse(e.getMessage()));
        }

    }

    @GetMapping(path = "/{id}/salary/max")
    public ResponseEntity<?> employeeMaxSalary(@PathVariable("id") int department) {
        try {
            return ResponseEntity.ok(departmentService.getSalaryMax(department));
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(departmentService.errorResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "/{id}/salary/sum")
    public ResponseEntity<?> employeeSumSalary(@PathVariable("id") int department) {
        try {
        return ResponseEntity.ok(departmentService.getSalarySum(department));
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(departmentService.errorResponse(e.getMessage()));
        }
    }
}


