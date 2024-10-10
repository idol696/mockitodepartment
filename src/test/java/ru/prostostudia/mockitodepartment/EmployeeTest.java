package ru.prostostudia.mockitodepartment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    Employee testEmployee = new Employee("Дарья", "Коробкова",1,100.56);
    @Test
    @DisplayName("Метод id() - возвращение ключа")
    void id() {
        assertEquals("дарьякоробкова",testEmployee.id());
    }

    @Test
    @DisplayName("Метод getFirstName() - получение имени")
    void getFirstName() {
        assertEquals("Дарья",testEmployee.getFirstName());
    }

    @Test
    @DisplayName("Метод getLastName() - получение фамилии")
    void getLastName() {
        assertEquals("Коробкова",testEmployee.getLastName());
    }

    @Test
    @DisplayName("Метод setDepartment() - присвоение отдела и тест коррекции отрицательного")
    void setDepartment() {
        testEmployee.setDepartment(-2);
        assertEquals(0,testEmployee.getDepartment());
    }

    @Test
    @DisplayName("Метод setSalary() - присвоение ЗП и тест коррекции отрицательного")
    void setSalary() {
        testEmployee.setSalary(-2);
        assertEquals(0,testEmployee.getSalary());
    }

    @Test
    @DisplayName("Метод getDepartment() - получение отдела")
    void getDepartment() {
        assertEquals(1,testEmployee.getDepartment());
    }

    @Test
    @DisplayName("Метод getSalary() - получение ЗП")
    void getSalary() {
        assertEquals(100.56,testEmployee.getSalary());
    }

    @Test
    @DisplayName("Метод toString() - получение текстового представления объекта")
    void testToString() {
        assertEquals("Дарья Коробкова", testEmployee.toString());
    }

    @Test
    @DisplayName("Метод equals() - сравнение объектов")
    void testEquals() {
        Employee explainEmployee = new Employee("Дарья","Коробкова");
        assertEquals(testEmployee, explainEmployee);
    }

    @Test
    @DisplayName("Метод Конструктор без параметров - ошибка Конструктора")
    void testConstructor() {
        assertThrows(RuntimeException.class, Employee::new);
    }
    @Test
    @DisplayName("Метод Конструктор отрицательный отдел - ошибка Конструктора")
    void testConstructor_RuntimeException_Department() {
        assertThrows(RuntimeException.class, () -> new Employee(null,null,-1,0));
    }
    @Test
    @DisplayName("Метод Конструктор отрицательная ЗП - ошибка Конструктора")
    void testConstructor_RuntimeException_Salary() {
        assertThrows(RuntimeException.class, () -> new Employee(null,null,0,-1));
    }

    @Test
    @DisplayName("Метод Конструктор только фамилия - проверки на имя/фамилию не должно быть")
    void testConstructor_StringString() {
        assertDoesNotThrow(()-> new Employee(null,null));
    }
    @Test
    @DisplayName("Метод Конструктор полный - проверки на имя/фамилию не должно быть")
    void testConstructor_FullParameter() {
        assertDoesNotThrow(()-> new Employee(null,null, 0,0));
    }

    @Test
    @DisplayName("Хэши")
    void hashCodeTest() {
        assertEquals(Objects.hash("Дарья", "Коробкова"),testEmployee.hashCode());
    }
}