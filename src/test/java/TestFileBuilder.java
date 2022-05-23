import org.backend.Main;
import org.backend.controllers.PostController;
import static org.mockito.Mockito.*;
import org.backend.data.DataBuilder;
import org.backend.models.Employee;
import org.backend.models.PostEmployee;
import org.backend.files.FileDataBuilder;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.backend.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestFileBuilder {
    DataBuilder dataBuilder;

    @BeforeEach
    void beforeEach() {
        dataBuilder = new FileDataBuilder(new CheckData());
    }



    @BeforeAll
    static void beforeAll() {
        //Create and replace PostController
        PostController postControllerMock = mock(PostController.class);
        Main.postController = postControllerMock;
    }

    @Test
    void testOkParseDataEmployees() {
        //TODO Unknown error
        //Arrange
        String data = "";
        List<Employee> employeesOrig = new ArrayList<Employee>();
        PostEmployee postEmployee = new PostEmployee(UUID.randomUUID(),RandomString.make(8));

        PostController postController = mock(PostController.class);
        when(postController.getObjectById(postEmployee.getId())).thenReturn(postEmployee);
        Main.postController = postController;

        for(int i = 0;i<10;i++)
        {
            Employee employee = CreateRandomEmployee();
            employee.setPost(postEmployee);
            employeesOrig.add(employee);
        }
        data = dataBuilder.getSaveString(employeesOrig.toArray(new Employee[0]));
        //Act
        Employee[] employees = dataBuilder.parseEmployees(data);
        //Assert
        assertThat(employees)
                .containsOnly(employeesOrig.toArray(new Employee[0]));
    }

    @Test
    void testErrorParseDataEmployees() {
        //Arrange


        String data = RandomString.make(10);
        //Act
        //Assert
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> {
                    dataBuilder.parseEmployees(data);
                })
                .withMessage("Invalid data format");
    }

    @Test
    void testOkParseDataPosts() {
        //Arrange
        String data = "";
        List<PostEmployee> postEmployeesOrig = new ArrayList<PostEmployee>();
        for(int i = 0;i<10;i++)
        {
            postEmployeesOrig.add(new PostEmployee(UUID.randomUUID(),RandomString.make(10)));
        }
        data = dataBuilder.getSaveString(postEmployeesOrig.toArray(new PostEmployee[0]));
        //Act
        PostEmployee[] postEmployees = dataBuilder.parsePostEmployees(data);
        //Assert
        assertThat(postEmployees)
                .containsOnly(postEmployeesOrig.toArray(new PostEmployee[] {}));
    }

    @Test
    void testErrorParseDataPosts() {
        //Arrange


        String data = RandomString.make(10);
        //Act
        //Assert
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> {
                    dataBuilder.parsePostEmployees(data);
                })
                .withMessage("Invalid posts data format");
    }

    @Test
    void testOkGetStringEmployees() {
        //Arrange
        List<Employee> employees = new ArrayList<Employee>();
        List<String> trueDataStrings = new ArrayList<>();
        String trueData = "";
        for(int i = 0;i<3;i++)
        {
            employees.add(CreateRandomEmployee());
            trueDataStrings.add(employees.get(i).toSaveString());
        }
        trueData = String.join("\n\n", trueDataStrings);
        //Act
        String result = dataBuilder.getSaveString(employees.toArray(new Employee[] {}));
        //Assert
        assertThat(result)
                .isEqualTo(trueData);

    }
    @Test
    void testOkGetStringPosts() {
        //Arrange
        List<PostEmployee> postEmployees = new ArrayList<PostEmployee>();
        List<String> trueDataStrings = new ArrayList<>();
        String trueData = "";
        for(int i = 0;i<3;i++)
        {
            postEmployees.add(new PostEmployee(UUID.randomUUID(),RandomString.make(10)));
            trueDataStrings.add(postEmployees.get(i).toSaveString());
        }
        trueData = String.join("\n\n", trueDataStrings);
        //Act
        String result = dataBuilder.getSaveString(postEmployees.toArray(new PostEmployee[] {}));
        //Assert
        assertThat(result)
                .isEqualTo(trueData);

    }

    private Employee CreateRandomEmployee()
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
