package ru.prostostudia.mockitodepartment.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.prostostudia.mockitodepartment.Employee;
import ru.prostostudia.mockitodepartment.exceptions.EmployeeAlreadyAddedException;
import ru.prostostudia.mockitodepartment.exceptions.EmployeeNotFoundException;
import ru.prostostudia.mockitodepartment.exceptions.EmployeeStorageIsFullException;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class EmployeeServiceImplTest {

    EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

    @ParameterizedTest
    @DisplayName("Метод AddEmployee(String,String) - добавление имени и фамилии (без дублей и лимитов)")
    @MethodSource({"parametersPrimitiveForBasicEmployeeServiceTest","parametersNegativeNameForAddServiceTest"})
    void testAddEmployee_AddEmployee_NameTest_KeyAndNameValid(String message, String firstName, String lastName, int department, double salary, boolean testPositive) {

        Map<String, Employee> employees = employeeService.getEmployees();
        if (!testPositive) {
            assertThrows(RuntimeException.class, () -> employeeService.addEmployee(firstName, lastName));
        } else {
            employeeService.addEmployee(firstName, lastName);
            assertTrue(employees.containsKey((firstName + lastName).toLowerCase()));
        }
    }

    @ParameterizedTest
    @DisplayName("Метод AddEmployee(String,String) - добавление через конструктор (без дублей и лимитов)")
    @MethodSource({"parametersPrimitiveForBasicEmployeeServiceTest","parametersNegativeNameForAddServiceTest","parametersNegativeForAddServiceTest"})
    void testAddEmployee_AddEmployee_NameTest_Constructor(String message, String firstName, String lastName, int department, double salary, boolean testPositive) {

        if (!testPositive) {
            assertThrows(RuntimeException.class, () -> employeeService.addEmployee(firstName, lastName, department, salary));
        } else {
            Map<String, Employee> explainEmployees = employeeService.getEmployees().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            employeeService.addEmployee(firstName, lastName, department, salary);
            Employee employee = new Employee(firstName, lastName, department, salary);
            explainEmployees.put((firstName + lastName).toLowerCase(),employee);
            Map<String, Employee> actualEmployees = employeeService.getEmployees();
            assertEquals(explainEmployees,actualEmployees);
        }
    }

    @Test
    @DisplayName("Метод AddEmployee(String,String,int,double) - лимиты: урезка EmployeeStorageIsFullException")
    void testAddEmployee_AddEmployee_EmployeeStorageIsFullException_Shrink() {

        Stream<Arguments> argumentsStream = parametersPrimitiveForBasicEmployeeServiceTest();

        argumentsStream.forEach(arguments -> {
                    Object[] args = arguments.get();
                    String firstName = (String) args[1];
                    String lastName = (String) args[2];
                    int department = (int) args[3];
                    double salary = Double.parseDouble( String.valueOf(args[4]));

                    employeeService.addEmployee(firstName, lastName, department, salary);
                });
        assertThrows(EmployeeStorageIsFullException.class, () -> employeeService.setMaxEmployees(5));
    }

    @Test
    @DisplayName("Метод AddEmployee(String,String,int,double) - лимиты: переполнение EmployeeStorageIsFullException")
    void testAddEmployee_AddEmployee_EmployeeStorageIsFullException_Override() {

        Stream<Arguments> argumentsStream = parametersPrimitiveForBasicEmployeeServiceTest();
        employeeService.setMaxEmployees(10);

        argumentsStream.forEach(arguments -> {
            Object[] args = arguments.get();
            String firstName = (String) args[1];
            String lastName = (String) args[2];
            int department = (int) args[3];
            double salary = Double.parseDouble( String.valueOf(args[4]));

            employeeService.addEmployee(firstName, lastName, department, salary);
        });
        assertThrows(EmployeeStorageIsFullException.class, () ->employeeService.addEmployee("Буба", "Касторский"));
    }

    @Test
    @DisplayName("Метод AddEmployee(String,String,int,double) - повторное добавление: EmployeeAlreadyAddonException")
    void testAddEmployee_AddEmployee_EmployeeAlreadyAddonException_Double() {

        employeeService.addEmployee("Буба", "Касторский");
        assertThrows(EmployeeAlreadyAddedException.class, () ->employeeService.addEmployee("Буба", "Касторский"));
    }

    @Test
    @DisplayName("Метод AddEmployee(String,String) - добавление по имени, фамилии - возвращаемое значение")
    void testAddEmployee_AddEmployee_ReturnValue() {

        employeeService.addEmployee("Буба", "Касторский");
        assertThrows(EmployeeAlreadyAddedException.class, () ->employeeService.addEmployee("Буба", "Касторский"));
    }

    @ParameterizedTest
    @DisplayName("Метод AddEmployee(String,String,int,double) - добавление через конструктор (прочие исключения)")
    @MethodSource({"parametersNegativeForAddServiceTest"})
    void testAddEmployee_AddEmployee_NameTest_ExceptionOther(String message, String firstName, String lastName, int department, double salary, boolean testPositive) {

        if (!testPositive) {
            assertThrows(RuntimeException.class, () -> employeeService.addEmployee(firstName, lastName, department, salary));
        } else {
            Map<String, Employee> explainEmployees = employeeService.getEmployees().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            employeeService.addEmployee(firstName, lastName, department, salary);
            Employee employee = new Employee(firstName, lastName, department, salary);
            explainEmployees.put((firstName + lastName).toLowerCase(),employee);
            Map<String, Employee> actualEmployees = employeeService.getEmployees();
            assertEquals(explainEmployees,actualEmployees);
        }
    }

    @Test
    @DisplayName("Метод FindEmployee - поиск")
    void testAddEmployee_FindEmployee() {

        Stream<Arguments> argumentsStream = parametersPrimitiveForBasicEmployeeServiceTest();

        argumentsStream.forEach(arguments -> {
            Object[] args = arguments.get();
            String firstName = (String) args[1];
            String lastName = (String) args[2];
            int department = (int) args[3];
            double salary = Double.parseDouble( String.valueOf(args[4]));

            employeeService.addEmployee(firstName, lastName, department, salary);
        });
        Employee explainEmployee = new Employee("Игорь","Мусинькин",0,0);
        assertEquals(explainEmployee, employeeService.findEmployee("Игорь", "Мусинькин"));
    }

    @Test
    @DisplayName("Метод FindEmployee - поиск: ошибка EmployeeNotFoundException")
    void testAddEmployee_FindEmployee_EmployeeNotFoundException() {

        employeeService.addEmployee("Игорь","Мусинькин",0,0);
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployee("",""));
    }

    @Test
    @DisplayName("Метод deleteEmployee - удаление")
    void testAddEmployee_deleteEmployee() {

        Stream<Arguments> argumentsStream = parametersPrimitiveForBasicEmployeeServiceTest();

        argumentsStream.forEach(arguments -> {
            Object[] args = arguments.get();
            String firstName = (String) args[1];
            String lastName = (String) args[2];
            int department = (int) args[3];
            double salary = Double.parseDouble( String.valueOf(args[4]));

            employeeService.addEmployee(firstName, lastName, department, salary);
        });

        employeeService.deleteEmployee("Игорь", "Мусинькин");
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployee("Игорь", "Мусинькин"));
    }

    @Test
    @DisplayName("Метод getMaxEmployee, setMaxEmployee - установить и получить максимальное количество сотрудников")
    void test_getMaxEmployees_setMaxEmployee() {
        employeeService.setMaxEmployees(8);
        assertEquals(8,employeeService.getMaxEmployees());
    }

    @Test
    @DisplayName("Метод errorResponse - проверка возврата ошибки")
    void test_errorResponse() {
        Map<String, String> result = employeeService.errorResponse("message");
        assertEquals("message", result.get("error"));
    }

    private static Stream<Arguments> parametersPrimitiveForBasicEmployeeServiceTest() {
        return Stream.of(Arguments.of("1 Сотрудник (1 отд)", "Илья", "Бабушкин", 1, 10_000, true),
                Arguments.of("2 сотрудник (макс ЗП 1 отд)", "Игорь", "Мусинькин", 1, 20_000, true),
                Arguments.of("3 сотрудник (1 отд)", "Виталий", "Хазбулатов", 1, 5000, true),
                Arguments.of("Английский(2-'о'):4 сотрудник (2 отд)", "Долдon", "Дoлдоев", 2, 10_000, true),
                Arguments.of("2-я Заглавная в фамилии:5 сотрудник (2 отд)", "Исаакий", "ВолонДемортов", 2, 30_000, true),
                Arguments.of("6 сотрудник (1 отд)", "Ирина", "Дудина", 1, 3000, true),
                Arguments.of("Нулевой отдел:7 сотрудник (0 отд)", "Иннокентий", "Смактуновский", 0, 5000, true),
                Arguments.of("8 сотрудник (3 отд)", "Наталья", "Бузинова", 3, 80_000, true),
                Arguments.of("Нулевая зарплата:9 сотрудник (3 отд)", "Навелий", "Навеяло", 3, 0, true),
                Arguments.of("10 сотрудник (2 отд)", "Прасковья", "Прошкина", 2, 30_000, true)
        );
    }

    private static Stream<Arguments> parametersNegativeNameForAddServiceTest() {
        return Stream.of(
                Arguments.of("Тест исключения:Всё пустое", "", "", 0, 0, false),
                Arguments.of("Тест исключения:Всё null и 0", null, null, 0, 0, false),
                Arguments.of("Тест исключения:null в имени", null, "Мусинькин", 1, 20_000, false),
                Arguments.of("Тест исключения:null в фамилии", "Виталий", null, 1, 5000, false),
                Arguments.of("Тест исключения:Пустое имя и фамилия", "", "", 2, 5000, false),
                Arguments.of("Тест исключения:Псевдосимвол в имени", "Кукс_", "Мукс_", 2, 5000, false),
                Arguments.of("Тест исключения:Псевдосимвол в фамилии", "", "", 2, 5000, false)
                );
    }

    private static Stream<Arguments> parametersNegativeForAddServiceTest() {
        return Stream.of(
                Arguments.of("Тест исключения:Отрицательная зарплата", "Иван", "Иванович", 2, -10_000, false),
                Arguments.of("Тест исключения:Отрицательный id отдела", "Ирина", "Дудина", -1, 3000, false),
                Arguments.of("Тест исключения:Отрицательный отдел и зарплата", "Наталья", "Кузинова", -3, -80_000, false)
        );

    }
}