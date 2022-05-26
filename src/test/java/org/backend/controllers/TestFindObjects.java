package org.backend.controllers;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.backend.Main;
import org.backend.controllers.EmployeeController;
import org.backend.controllers.PostController;
import org.backend.models.Employee;
import org.backend.models.PostEmployee;
import org.backend.files.FileDataBuilder;
import org.backend.files.FileSaverAndLoader;
import org.backend.observer.Observer;
import org.backend.utils.CheckData;
import org.backend.utils.FindArgument;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class TestFindObjects {
    static PostEmployee parent;

    @BeforeAll
    static void beforeAll(@TempDir Path dir) {
        Main.observer = mock(Observer.class);
        Main.postController = new PostController(
                new FileSaverAndLoader(
                        new CheckData(),
                        new FileDataBuilder(new CheckData())),
                Paths.get(dir.toString(),RandomString.make(5)+".txt"));
        Main.employeeController = new EmployeeController(
                new FileSaverAndLoader(
                        new CheckData(),
                        new FileDataBuilder(new CheckData())),
                Paths.get(dir.toString(),RandomString.make(5)+".txt"));
    }

    @BeforeEach
    void beforeEach() {
        parent = createRandomPost();
    }

    @Test
    void testFindPostByPartiallyName() {
        //Arrange
        List<PostEmployee> posts = new ArrayList<>();
        posts.add(parent);
        for(int i = 0;i<3;i++)
        {
            posts.add(createRandomPost());
        }
        Main.postController.setAllObjects(posts.stream().collect(Collectors.toMap(PostEmployee::getId, Function.identity())));
        //Act
        UUID[] result = Main.postController.findIdsByName(parent.getName().substring(2,5));
        //Assert
        assertThat(result)
                .contains(parent.getId());
    }

    @Test
    void testFindEmployeeByPartiallyName() {
        //Arrange
        List<Employee> employees = new ArrayList<>();
        Employee temp = createRandomEmployee();
        employees.add(temp);
        for(int i = 0;i<3;i++)
        {
            employees.add(createRandomEmployee());
        }
        Main.employeeController.setAllObjects(employees.stream().collect(Collectors.toMap(Employee::getId, Function.identity())));
        //Act
        UUID[] result = Main.employeeController.findIdsByName(temp.getName().substring(3,6));
        //Assert
        assertThat(result[0])
                .isEqualTo(temp.getId());
    }

    @Test
    void testFindPostByName() {
        //Arrange
        List<PostEmployee> posts = new ArrayList<>();
        posts.add(parent);
        for(int i = 0;i<3;i++)
        {
            posts.add(createRandomPost());
        }
        Main.postController.setAllObjects(posts.stream().collect(Collectors.toMap(PostEmployee::getId, Function.identity())));
        //Act
        UUID[] result = Main.postController.findIdsByName(parent.getName());
        //Assert
        assertThat(result[0])
                .isEqualTo(parent.getId());
    }

    @Test
    void testFindEmployeeByName() {
        //Arrange
        List<Employee> employees = new ArrayList<>();
        Employee temp = createRandomEmployee();
        employees.add(temp);
        for(int i = 0;i<3;i++)
        {
            employees.add(createRandomEmployee());
        }
        Main.employeeController.setAllObjects(employees.stream().collect(Collectors.toMap(Employee::getId, Function.identity())));
        //Act
        UUID[] result = Main.employeeController.findIdsByName(temp.getName());
        //Assert
        assertThat(result[0])
                .isEqualTo(temp.getId());
    }

    @Test
    void testFindEmployeeByNameAndPost() {
        //Arrange
        List<Employee> employees = new ArrayList<>();
        Employee temp = createRandomEmployee();
        employees.add(temp);
        for(int i = 0;i<3;i++)
        {
            employees.add(createRandomEmployee());
        }
        Main.employeeController.setAllObjects(employees.stream().collect(Collectors.toMap(Employee::getId, Function.identity())));
        //Act
        UUID[] result = Main.employeeController.findFilteredEmployeesByPost(FindArgument.builder()
                .name(temp.getName())
                .build(),
                temp.getPost());
        //Assert
        assertThat(result[0])
                .isEqualTo(temp.getId());
    }

    private PostEmployee createRandomPost()
    {
        return new PostEmployee(UUID.randomUUID(), RandomString.make(8));
    }

    private Employee createRandomEmployee()
    {
        return Employee.builder()
                .firstName(RandomString.make(8))
                .lastName(RandomString.make(8))
                .characteristics(new String[] {
                        RandomString.make(8)
                })
                .post(new PostEmployee(UUID.randomUUID(),RandomString.make(8)))
                .id(UUID.randomUUID())
                .build();
    }
}
