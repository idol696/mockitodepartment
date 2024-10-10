package ru.prostostudia.mockitodepartment.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.prostostudia.mockitodepartment.Employee;
import ru.prostostudia.mockitodepartment.exceptions.EmployeeNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

class DepartmentServiceImplTest {

    @Mock
    private EmployeeServiceImpl employeeService;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Метод getEmployeesInDepartment - возврат сотрудников в указанном отделе")
    void testGetEmployeesInDepartment() {
        Employee employee1 = new Employee("Петя", "Пупкин", 1, 50000);
        Employee employee2 = new Employee("Дарья", "Коробкова", 1, 60000);
        Map<String, Employee> employees = new HashMap<>();
        employees.put("петяпупкин", employee1);
        employees.put("дарьякоробкова", employee2);

        when(employeeService.getEmployees()).thenReturn(employees);

        Map<String, Employee> result = departmentService.getEmployeesInDepartment(1);
        assertEquals(2, result.size(),"Тест количества сотрудников: провален");
        assertTrue(result.containsKey("петяпупкин"),"Сотрудник 1: ключ не совпадает");
        assertTrue(result.containsKey("дарьякоробкова"),"Сотрудник 2: ключ не совпадает");
    }

    @Test
    @DisplayName("Метод getSalaryMin - минимальная ЗП сотрудников в указанном отделе")
    void testGetSalaryMin() {
        Employee employee1 = new Employee("Петя", "Пупкин", 1, 50000.50);
        Employee employee2 = new Employee("Дарья", "Коробкова", 1, 60000);
        Employee employee3 = new Employee("Фёдор", "Двинятин", 2, 30000);
        Map<String, Employee> employees = new HashMap<>();
        employees.put("петяпупкин", employee1);
        employees.put("дарьякоробкова", employee2);
        employees.put("фёдордвинятин", employee3);

        when(employeeService.getEmployees()).thenReturn(employees);
        Employee result = departmentService.getSalaryMin(1);
        assertEquals(50000.50, result.getSalary());
    }

    @Test
    @DisplayName("Метод getSalaryMax - максимальная ЗП сотрудников в указанном отделе")
    void testGetSalaryMax() {
        Employee employee1 = new Employee("Петя", "Пупкин", 1, 50000.50);
        Employee employee2 = new Employee("Дарья", "Коробкова", 1, 60000);
        Employee employee3 = new Employee("Фёдор", "Двинятин", 2, 30000);
        Map<String, Employee> employees = new HashMap<>();
        employees.put("петяпупкин", employee1);
        employees.put("дарьякоробкова", employee2);
        employees.put("фёдордвинятин", employee3);

        when(employeeService.getEmployees()).thenReturn(employees);
        Employee result = departmentService.getSalaryMax(1);
        assertEquals(60000, result.getSalary());
    }

    @Test
    @DisplayName("Метод getSalaryMin - нет сотрудников в отделе")
    void testGetSalaryMin_EmployeeNotFoundException() {
        Employee employee1 = new Employee("Петя", "Пупкин", 1, 50000.50);
        Map<String, Employee> employees = new HashMap<>();
        employees.put("петяпупкин", employee1);

        when(employeeService.getEmployees()).thenReturn(employees);
        assertThrows(EmployeeNotFoundException.class,()-> departmentService.getSalaryMin(2));
    }

    @Test
    @DisplayName("Метод getSalaryMax - нет сотрудников в отделе")
    void testGetSalaryMax_EmployeeNotFoundException() {
        Employee employee1 = new Employee("Петя", "Пупкин", 1, 50000.50);
        Map<String, Employee> employees = new HashMap<>();
        employees.put("петяпупкин", employee1);

        when(employeeService.getEmployees()).thenReturn(employees);
        assertThrows(EmployeeNotFoundException.class,()-> departmentService.getSalaryMax(2));
    }

    @Test
    @DisplayName("Метод getEmployeesGroupByDepartment() - сотрудники по отделам")
    void testGetEmployeesGroupByDepartment() {
        Employee employee1 = new Employee("Петя", "Пупкин", 1, 50000.50);
        Employee employee2 = new Employee("Дарья", "Коробкова", 1, 60000);
        Employee employee3 = new Employee("Фёдор", "Двинятин", 2, 30000);
        Map<String, Employee> employees = new HashMap<>();
        employees.put("петяпупкин", employee1);
        employees.put("дарьякоробкова", employee2);
        employees.put("фёдордвинятин", employee3);

        when(employeeService.getEmployees()).thenReturn(employees);
        Map<Integer, List<Employee>> result = departmentService.getEmployeesGroupByDepartment();
        assertEquals(2, result.size(),"Всего отделов: провалено");
        assertEquals(2, result.get(1).size(),"Отдел 1: провалено");
        assertEquals(1, result.get(2).size(),"Отдел 2: провалено");
    }

    @Test
    @DisplayName("Метод getSalarySum() - сумма ЗП по отделам")
    void testGetSalarySum() {
        Employee employee1 = new Employee("Петя", "Пупкин", 1, 50000.50);
        Employee employee2 = new Employee("Дарья", "Коробкова", 1, 60000);
        Employee employee3 = new Employee("Фёдор", "Двинятин", 2, 30000);
        Map<String, Employee> employees = new HashMap<>();
        employees.put("петяпупкин", employee1);
        employees.put("дарьякоробкова", employee2);
        employees.put("фёдордвинятин", employee3);

        when(employeeService.getEmployees()).thenReturn(employees);
        Map<String, Double> result = departmentService.getSalarySum(1);
        assertEquals(110000.50, result.get("salarySum"));

    }

    @Test
    @DisplayName("Метод errorResponse() - метод возврата ошибки")
    void testErrorResponse() {
        Map<String, String> result = departmentService.errorResponse("message");
        assertEquals("message", result.get("error"));
    }
}