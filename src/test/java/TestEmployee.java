import static org.assertj.core.api.Assertions.*;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class TestEmployee {

    @Test
    void testCreateEmployee() {
        //Arrange
        String firstName = RandomString.make(8);
        String lastName = RandomString.make(7);
        String description = "";
        String[] characteristics = new String[]
                {
                        RandomString.make(7)
                };
        String postId = UUID.randomUUID().toString();
        PostEmployee post = new PostEmployee(postId, RandomString.make(7));
        String id = UUID.randomUUID().toString();
        //Act
        Employee employee = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .description(description)
                .characteristics(characteristics)
                .post(post)
                .id(id)
                .build();
        //Assert
        assertThat(employee.getFirstName())
                .isEqualTo(firstName);
        assertThat(employee.getLastName())
                .isEqualTo(lastName);
    }

    @Test
    void testErrorCreateEmployee() {
        //Arrange
        String firstName = RandomString.make(8);
        String lastName = RandomString.make(7);
        String description = "";
        String[] characteristics = new String[]
                {
                        RandomString.make(7)
                };
        String postId = UUID.randomUUID().toString();
        PostEmployee post = new PostEmployee(postId, RandomString.make(7));
        String id = UUID.randomUUID().toString();
        //Act
        //Assert
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(Employee.builder()
                .firstName(firstName)
                .build());
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build());

    }

    @Test
    void testEmployeeToString() {
        //Arrange
        String firstName = RandomString.make(8);
        String lastName = RandomString.make(7);
        String description = "";
        String[] characteristics = new String[]
                {
                        RandomString.make(7)
                };
        String postId = UUID.randomUUID().toString();
        PostEmployee post = new PostEmployee(postId, RandomString.make(7));
        String id = UUID.randomUUID().toString();
        String trueString = "Имя: " + firstName + "\n" +
                "Фамилия: " + lastName + "\n" +
                "Описание: " + description + "\n" +
                "Характеристика: " + "\n" +
                "\t" + characteristics[0] + ";\n" +
                "Должность: " + post.getName();
        //Act
        Employee employee = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .description(description)
                .characteristics(characteristics)
                .post(post)
                .id(id)
                .build();
        String string = employee.toString();
        //Assert
        assertThat(string)
                .isEqualTo(trueString);
    }

    @Test
    void testEmployeeToSaveString() {
        //Arrange
        String firstName = RandomString.make(8);
        String lastName = RandomString.make(7);
        String description = RandomString.make(7);
        String[] characteristics = new String[]
                {
                        RandomString.make(7)
                };
        String postId = UUID.randomUUID().toString();
        PostEmployee post = new PostEmployee(postId, RandomString.make(7));
        String id = UUID.randomUUID().toString();
        String trueString = "firstName: " + firstName + "\n" +
                "lastName: " + lastName + "\n" +
                "description: " + description + "\n" +
                "characteristics: " + characteristics[0] + ";\n" +
                "postId: " + post.getID()+"\n" +
                "image: none";
        //Act
        Employee employee = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .description(description)
                .characteristics(characteristics)
                .post(post)
                .id(id)
                .build();
        String string = employee.toSaveString();
        //Assert
        assertThat(string)
                .isEqualTo(trueString);
    }

    @Test
    void testEmployeeToSaveStringNoDescription() {
        //Arrange
        String firstName = RandomString.make(8);
        String lastName = RandomString.make(7);
        String[] characteristics = new String[]
                {
                        RandomString.make(7)
                };
        String postId = UUID.randomUUID().toString();
        PostEmployee post = new PostEmployee(postId, RandomString.make(7));
        String id = UUID.randomUUID().toString();
        String trueString = "id: " + id +"\n" +
                "firstName: " + firstName + "\n" +
                "lastName: " + lastName + "\n" +
                "description: none\n" +
                "characteristics: " + characteristics[0] + ";\n" +
                "postId: " + post.getID()+"\n" +
                "image: none";
        //Act
        Employee employee = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .characteristics(characteristics)
                .post(post)
                .id(id)
                .build();
        String string = employee.toSaveString();
        //Assert
        assertThat(string)
                .isEqualTo(trueString);
    }
}
