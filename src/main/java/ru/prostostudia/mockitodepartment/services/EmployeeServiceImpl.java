package ru.prostostudia.mockitodepartment.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.prostostudia.mockitodepartment.Employee;
import ru.prostostudia.mockitodepartment.interfaces.EmployeeService;
import ru.prostostudia.mockitodepartment.exceptions.*;


import java.util.HashMap;
import java.util.Map;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Map<String, Employee> employeesBook = new HashMap<>(Map.of());
    private int maxEmployees = Integer.MAX_VALUE;

    private boolean isNameCorrect(String name) {

        return StringUtils.isAllUpperCase(name.subSequence(0,1)) && StringUtils.isAlpha(name);
    }

    public int getMaxEmployees() {
        return maxEmployees;
    }

    public void setMaxEmployees(int maxEmployees) {
        if (employeesBook.size() > maxEmployees) throw new EmployeeStorageIsFullException();
        this.maxEmployees = maxEmployees;
    }

    @Override
    public Map<String, Employee> getEmployees() {
        return employeesBook;
    }

    @Override
    public Employee addEmployee(String firstName, String lastName)  {
        if (!(isNameCorrect(firstName) && isNameCorrect(lastName)) ) {
            throw new EmployeeBadRequest();
        }
        if (employeesBook.size() >= maxEmployees) throw new EmployeeStorageIsFullException();
        try {
            findEmployee(firstName, lastName);
        } catch (EmployeeNotFoundException e) {
            Employee employee = new Employee(firstName, lastName);
            employeesBook.put(employee.id(), employee);
            return employee;
        }
        throw new EmployeeAlreadyAddedException();
    }

    @Override
    public void addEmployee(String firstName, String lastName, int department, double salary) {
        if (!(isNameCorrect(firstName) && isNameCorrect(lastName)) ) {
            throw new RuntimeException("Первые буквы Имени и Фамилии должны быть заглавными!");
        }
        final Employee newemployee = new Employee(firstName, lastName, department, salary);
        employeesBook.put(newemployee.id(), newemployee);
    }

    @Override
    public Employee deleteEmployee(String firstName, String lastName) {
        Employee employee = findEmployee(firstName, lastName);
        employeesBook.remove(employee.id());
        return employee;
    }

    @Override
    public Employee findEmployee(String firstName, String lastName) {
        String id = new Employee(firstName, lastName).id();
        if (employeesBook.containsKey(id)) {
            return employeesBook.get(id);
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public Map<String,String> errorResponse(String errorMessage) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", errorMessage);
        return errorResponse;
    }

    public void demoFill() {
        employeesBook.clear();
        addEmployee("Илья", "Бабушкин", 1, 10_000);
        addEmployee("Игорь", "Мусинькин", 1, 20_000);
        addEmployee("Виталий", "Хазбулатов", 1, 5000);
        addEmployee("Иван", "Познер", 2, 10_000);
        addEmployee("Исаакий", "Волондемортов", 2, 30_000);
        addEmployee("Ирина", "Дудина", 1, 3000);
        addEmployee("Иннокентий", "Смактуновский", 2, 5000);
        addEmployee("Наталья", "Бузинова", 3, 80_000);
        addEmployee("Навелий", "Навеяло", 3, 50_000);
        addEmployee("Прасковья", "Прошкина", 2, 30_000);
        setMaxEmployees(10);
    }
}

