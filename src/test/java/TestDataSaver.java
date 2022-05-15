import org.backend.data.DataBuilder;
import org.backend.data.DataSaver;
import org.backend.employee.Employee;
import org.backend.employee.PostEmployee;
import org.backend.files.FileDataBuilder;
import org.backend.files.FileSaver;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.backend.utils.CheckData;

import static org.assertj.core.api.Assertions.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestDataSaver {
    DataSaver dataSaver;
    DataBuilder dataBuilder;

    @BeforeEach
    void beforeEach() {
        dataBuilder = new FileDataBuilder(new CheckData());
        dataSaver = new FileSaver(new CheckData(),dataBuilder);
    }
    @Test
    void testDataSaverIsInterface() {
        //Arrange
        //Act
        //Assert
        assertThat(DataSaver.class)
                .isInterface();
    }

    @Test
    void testOkDataEmployeesSave(@TempDir Path dir) {
        //Arrange
        DataBuilder dataBuilder = new FileDataBuilder(dataSaver.getCheck());
        Path file = Path.of(dir.toString(),RandomString.make(5) + ".txt");
        Employee[] employees = CreateRandomEmployees(3);
        //Act
        dataSaver.createOrReplaceSaveData(employees,file);
        //Assert
        assertThat(file)
                .exists()
                .isNotEmptyFile()
                .hasContent(dataBuilder.getString(employees));
    }
    @Test
    void testOkDataPostsSave(@TempDir Path dir) {
        //Arrange
        DataBuilder dataBuilder = new FileDataBuilder(dataSaver.getCheck());
        Path file = Path.of(dir.toString(),RandomString.make(5) + ".txt");
        List<PostEmployee> postEmployees = new ArrayList<PostEmployee>();
        for(int i = 0;i<3;i++)
        {
            postEmployees.add(new PostEmployee(UUID.randomUUID(),RandomString.make(10)));
        }
        //Act
        dataSaver.createOrReplaceSaveData(postEmployees.toArray(new PostEmployee[] {}),file);
        //Assert
        assertThat(file)
                .exists()
                .isNotEmptyFile()
                .hasContent(dataBuilder.getString(postEmployees.toArray(new PostEmployee[] {})));
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
