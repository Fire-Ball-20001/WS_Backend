import com.sun.source.tree.UsesTree;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.Buffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TestDataSaver {
    DataSaver dataSaver;

    @BeforeEach
    void beforeEach() {
        dataSaver = new FileSaver(new CheckData());
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
        DataBuilder dataBuilder = new DataBuilder(dataSaver.getCheck());
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
        DataBuilder dataBuilder = new DataBuilder(dataSaver.getCheck());
        Path file = Path.of(dir.toString(),RandomString.make(5) + ".txt");
        List<PostEmployee> postEmployees = new ArrayList<PostEmloyee>();
        for(int i = 0;i<3;i++)
        {
            postEmployees.add(new PostEmployee(UUID.randomUUID().toString(),RandomString.make(10)));
        }
        //Act
        dataSaver.createOrReplaceSaveData(postEmployees,file);
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
