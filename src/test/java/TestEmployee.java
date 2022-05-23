import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.backend.Main;
import org.backend.controllers.PostController;
import org.backend.employee.Employee;
import org.backend.employee.PostEmployee;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class TestEmployee {

    @BeforeAll
    static void beforeAll() {
        Main.postController = mock(PostController.class);
    }

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
        UUID postId = UUID.randomUUID();
        PostEmployee post = new PostEmployee(postId, RandomString.make(7));
        UUID id = UUID.randomUUID();
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
        //Act
        //Assert
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> {
            Employee.builder()
                    .firstName(firstName)
                    .build();
        });
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> {
            Employee.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .build();
        });

    }

    @Test
    void testEmployeeToString() {
        //Arrange
        String firstName = RandomString.make(8);
        String lastName = RandomString.make(7);
        String description = RandomString.make(8);
        String[] characteristics = new String[]
                {
                        RandomString.make(7)
                };
        UUID postId = UUID.randomUUID();
        PostEmployee post = new PostEmployee(postId, RandomString.make(7));

        when(Main.postController.getObjectById(post.getId()))
                .thenReturn(post);

        UUID id = UUID.randomUUID();
        String trueString = "Имя: " + firstName + "\n" +
                "Фамилия: " + lastName + "\n" +
                "Описание: " + description + "\n" +
                "Должность: " + post.getName() + "\n" +
                "Характеристика: " + "\n" +
                "\t" + characteristics[0] + ";";
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
        UUID postId = UUID.randomUUID();
        PostEmployee post = new PostEmployee(postId, RandomString.make(7));

        when(Main.postController.getObjectById(post.getId()))
                .thenReturn(post);

        UUID id = UUID.randomUUID();
        String trueString = "id: "+id.toString() + "\n" +
                "firstName: " + firstName + "\n" +
                "lastName: " + lastName + "\n" +
                "description: " + description + "\n" +
                "postId: " + post.getId()+"\n" +
                "characteristics: " + characteristics[0] + "\n" +
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
        UUID postId = UUID.randomUUID();
        PostEmployee post = new PostEmployee(postId, RandomString.make(7));
        UUID id = UUID.randomUUID();
        String trueString = "id: " + id +"\n" +
                "firstName: " + firstName + "\n" +
                "lastName: " + lastName + "\n" +
                "description: none\n" +
                "postId: " + post.getId()+"\n" +
                "characteristics: " + characteristics[0] + "\n" +
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
