import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.*;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestDataLoader {
    DataLoader dataLoader;

    @BeforeEach
    void beforeEach() {
        dataLoader = new FileLoader(new CheckData());
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
        Path file = Path.of(dir.toString(), RandomString.make(5)+".txt");
        DataSaver dataSaver = new FileSaver(dataLoader.getCheck());
        Employee[] employees = CreateRandomEmployees(3);
        dataSaver.createOrReplaceSaveData(employees,file);
        //Act
        Employee[] load_employees = dataLoader.loadEmployeeData(file);
        //Assert
        assertThat(load_employees)
                .containsOnly(employees);
    }
    @Test
    void testOkPostsData(@TempDir Path dir){
        //Arrange
        Path file = Path.of(dir.toString(), RandomString.make(5)+".txt");
        DataSaver dataSaver = new FileSaver(dataLoader.getCheck());
        List<PostEmployee> postEmployees = new ArrayList<PostEmloyee>();
        for(int i = 0;i<3;i++)
        {
            postEmployees.add(new PostEmployee(UUID.randomUUID().toString(),RandomString.make(10)));
        }
        dataSaver.createOrReplaceSaveData(postEmployees,file);
        //Act
        PostEmployee[] load_employees = dataLoader.loadPostsData(file);
        //Assert
        assertThat(load_employees)
                .containsOnly(postEmployees.toArray(new PostEmployee[] {}));
    }

    @Test
    void testErrorLoadEmployeesData(@TempDir Path dir) throws Exception {
        //Arrange
        Path file = Path.of(dir.toString(), RandomString.make(5)+".txt");
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
                .isThrownBy(dataLoader.loadEmployeeData(file))
                .withMessage("Invalid employee data format");

    }
    @Test
    void testErrorPostsData(@TempDir Path dir) throws Exception {
        //Arrange
        Path file = Path.of(dir.toString(), RandomString.make(5)+".txt");
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
                .isThrownBy(dataLoader.loadPostsData(file))
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
                    .post(new PostEmployee(UUID.randomUUID(),RandomString.make(8)))
                    .id(UUID.randomUUID())
                    .build();
            employees.add(tempEmployee);
        }
        return employees.toArray(new Employee[] {});
    }
}
