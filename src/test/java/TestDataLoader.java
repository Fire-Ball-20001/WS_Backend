import org.backend.Main;
import org.backend.controllers.BaseController;
import org.backend.controllers.EmployeeController;
import org.backend.controllers.PostController;
import org.backend.data.DataBuilder;
import org.backend.data.DataLoader;
import org.backend.data.DataSaver;
import org.backend.employee.Employee;
import org.backend.employee.PostEmployee;
import org.backend.files.FileDataBuilder;
import org.backend.files.FileLoader;
import org.backend.files.FileSaver;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.backend.utils.CheckData;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestDataLoader {
    DataLoader dataLoader;
    DataBuilder dataBuilder;
    static PostEmployee parent;

    @BeforeEach
    void beforeEach() {
        dataBuilder = new FileDataBuilder(new CheckData());
        dataLoader = new FileLoader(new CheckData(),dataBuilder);
        //Create parent postEmployee
        parent = new PostEmployee(UUID.randomUUID(),RandomString.make(10));
    }

    @BeforeAll
    static void beforeAll() {
        //Create and replace EmployeeController
        EmployeeController employeeControllerMock = mock(EmployeeController.class);
        BaseController<Employee> baseControllerEmployeeMock = (BaseController<Employee>) mock(BaseController.class);
        when(baseControllerEmployeeMock.getDefaultFileName()).thenReturn(RandomString.make(8));
        when(employeeControllerMock.getBaseController()).thenReturn(baseControllerEmployeeMock);
        Main.employeeController = employeeControllerMock;

        //Create and replace PostController
        PostController postControllerMock = mock(PostController.class);
        BaseController<PostEmployee> baseControllerPostMock = (BaseController<PostEmployee>) mock(BaseController.class);
        when(baseControllerPostMock.getDefaultFileName()).thenReturn(RandomString.make(8));
        when(postControllerMock.getBaseController()).thenReturn(baseControllerPostMock);
        Main.postController = postControllerMock;
    }

    @Test
    void testDataLoaderIsInterface() {
        //Arrange
        //Act
        //Assert
        assertThat(DataLoader.class)
                .isInterface();
    }

    @Test
    void testOkLoadEmployeesData(@TempDir Path dir) {
        //Arrange
        Path file = Paths.get(dir.toString(), RandomString.make(5)+".txt");
        DataSaver dataSaver = new FileSaver(dataLoader.getCheck(),dataBuilder);
        Employee[] employees = CreateRandomEmployees(3);
        dataSaver.createOrReplaceSaveData(employees,file);

        //Updating postController

        when(Main.postController.getObjectById(parent.getId()))
                .thenReturn(parent);

        //Act
        Employee[] load_employees = dataLoader.loadEmployeesData(file);
        //Assert
        assertThat(load_employees)
                .containsOnly(employees);
    }
    @Test
    void testOkPostsData(@TempDir Path dir) {
        //Arrange
        Path file = Paths.get(dir.toString(), RandomString.make(5)+".txt");
        DataSaver dataSaver = new FileSaver(dataLoader.getCheck(),dataBuilder);
        List<PostEmployee> postEmployees = new ArrayList<PostEmployee>();
        for(int i = 0;i<3;i++)
        {
            postEmployees.add(new PostEmployee(UUID.randomUUID(),RandomString.make(10)));
        }
        dataSaver.createOrReplaceSaveData(postEmployees.toArray(new PostEmployee[] {}),file);
        //Act
        PostEmployee[] load_employees = dataLoader.loadPostsData(file);
        //Assert
        assertThat(load_employees)
                .containsOnly(postEmployees.toArray(new PostEmployee[] {}));
    }

    @Test
    void testErrorLoadEmployeesData(@TempDir Path dir) throws Exception {
        //Arrange
        Path file = Paths.get(dir.toString(), RandomString.make(5)+".txt");
        try(FileWriter fileWriter = new FileWriter(file.toString()))
        {
            fileWriter.write(RandomString.make(10));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            assert false;
        }
        //Act
        //Assert
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(()-> {
                    dataLoader.loadEmployeesData(file);
                })
                .withMessage("Invalid employee data format");

    }
    @Test
    void testErrorPostsData(@TempDir Path dir) throws Exception {
        //Arrange
        Path file = Paths.get(dir.toString(), RandomString.make(5)+".txt");
        try(FileWriter fileWriter = new FileWriter(file.toString()))
        {
            fileWriter.write(RandomString.make(10));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            assert false;
        }
        //Act
        //Assert
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(()-> {
                    dataLoader.loadPostsData(file);
                })
                .withMessage("Invalid post data format");
    }

    private Employee[] CreateRandomEmployees(int size)
    {
        List<Employee> employees = new ArrayList<Employee>();
        for(int i =0;i<size;i++)
        {
            Employee tempEmployee = Employee.builder()
                    .firstName(RandomString.make(8))
                    .lastName(RandomString.make(8))
                    .characteristics(new String[] {
                            RandomString.make(8)
                    })
                    .post(parent)
                    .id(UUID.randomUUID())
                    .build();
            employees.add(tempEmployee);
        }
        return employees.toArray(new Employee[] {});
    }
}
