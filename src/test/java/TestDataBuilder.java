import org.assertj.core.internal.bytebuddy.utility.RandomString;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestDataBuilder {
    DataBuilder dataBuilder;

    @BeforeEach
    void beforeEach() {
        dataBuilder = new DataBuilder();
        dataBuilder.setCheck(new CheckData());
    }

    @Test
    void testOkParseDataEmployees() {
        //Arrange
        StringBuilder data = new StringBuilder();
        List<Employee> employeesOrig = new ArrayList<Employee>();
        for(int i = 0;i<10;i++)
        {
            employeesOrig.add(CreateRandomEmployee());
            data.append(employeesOrig.get(employeesOrig.size()-1).toSaveString());
            data.append("\n\n");
        }
        //Act
        Employee[] employees = dataBuilder.parseEmployees(data.toString());
        //Assert
        assertThat(employees)
                .containsOnly(employeesOrig.toArray(new Employee[] {}));
    }

    @Test
    void testErrorParseDataEmployees() {
        //Arrange


        String data = RandomString.make(10);
        //Act
        //Assert
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(dataBuilder.parseEmployees(data))
                .withMessage("Invalid data format");
    }

    @Test
    void testOkParseDataPosts() {
        //Arrange
        StringBuilder data = new StringBuilder();
        List<PostEmployee> postEmployeesOrig = new ArrayList<PostEmployee>();
        for(int i = 0;i<10;i++)
        {
            postEmployeesOrig.add(new PostEmployee(UUID.randomUUID().toString(),RandomString.make(10)));
            data.append(postEmployeesOrig.get(postEmployeesOrig.size()-1).toString());
        }
        //Act
        PostEmployee[] postEmployees = dataBuilder.parsePostEmployees(data.toString());
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
                .isThrownBy(dataBuilder.parsePostEmployees(data))
                .withMessage("Invalid posts data format");
    }

    @Test
    void testOkGetStringEmployees() {
        //Arrange
        List<Employee> employees = new ArrayList<Employees>();
        StringBuilder trueData = new StringBuilder();
        for(int i = 0;i<3;i++)
        {
            employees.add(CreateRandomEmployee());
        }
        for (Employee emp:
             employees) {
            trueData.append(emp.toString());
            trueData.append("\n");
        }
        //Act
        String result = dataBuilder.getString(employees.toArray(new Employee[] {}));
        //Assert
        assertThat(result)
                .isEqualTo(trueData.toString());

    }
    @Test
    void testOkGetStringPosts() {
        //Arrange
        List<PostEmployee> postEmployees = new ArrayList<PostEmployees>();
        StringBuilder trueData = new StringBuilder();
        for(int i = 0;i<3;i++)
        {
            postEmployees.add(new PostEmployee(UUID.randomUUID().toString(),RandomString.make(10)));
        }
        for (PostEmployee post:
                postEmployees) {
            trueData.append(post.toString());
            trueData.append("\n");
        }
        //Act
        String result = dataBuilder.getString(postEmployees.toArray(new PostEmployee[] {}));
        //Assert
        assertThat(result)
                .isEqualTo(trueData.toString());

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
