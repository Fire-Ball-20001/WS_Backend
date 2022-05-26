package org.backend.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.backend.Main;
import org.backend.controllers.PostController;
import org.backend.data.DataBuilder;
import org.backend.models.Employee;
import org.backend.models.PostEmployee;
import org.backend.json.JsonBuilder;
import org.backend.utils.CheckData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestJsonBuilder {
    static PostEmployee parent;
    DataBuilder builder;


    @BeforeEach
    void beforeEach() {
        parent = new PostEmployee(UUID.randomUUID(),RandomString.make(8));
        when(Main.postController.getObjectById(parent.getId()))
                .thenReturn(parent);
        builder = new JsonBuilder(new CheckData());
    }

    @BeforeAll
    static void beforeAll() {
        Main.postController = mock(PostController.class);
    }

    @Test
    void testOkParseDataEmployee() {
        //Arrange
        Gson gson = new Gson();
        Main.gson = gson;
        Employee employee = createRandomEmployee();
        JsonArray element = new JsonArray();
        element.add(gson.toJsonTree(employee.getDto()));
        String str = gson.toJson(element);
        //Act
        Employee[] employees = builder.parseEmployees(str);
        //Assert
        assertThat(employees)
                .isNotEmpty()
                .contains(employee);
    }


    @Test
    void testErrorParseDataEmployee() {
        //Arrange
        Main.gson = new Gson();
        String str = RandomString.make(18);
        //Act
        //Assert
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> builder.parseEmployees(str))
                .withMessage("Invalid data format");

    }

    @Test
    void testGetSaveStringEmployee() {
        //Arrange
        Gson gson = new Gson();
        Main.gson = gson;
        Employee employee = createRandomEmployee();
        JsonArray element = new JsonArray();
        element.add(gson.toJsonTree(employee.getDto()));
        String true_str = gson.toJson(element);
        //Act
        String result = builder.getSaveString(new Employee[]{ employee});
        //Assert
        assertThat(result)
                .isNotEmpty()
                .isEqualTo(true_str);
    }

    private Employee createRandomEmployee()
    {
        return Employee.builder()
                .firstName(RandomString.make(8))
                .lastName(RandomString.make(8))
                .characteristics(new String[] {
                        RandomString.make(8)
                })
                .post(parent)
                .id(UUID.randomUUID())
                .build();
    }
}
