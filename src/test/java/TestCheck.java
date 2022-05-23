
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.backend.utils.Check;
import org.backend.utils.CheckData;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class TestCheck {
    Check check;



    @BeforeEach
    void BeforeEach() {
        check = new CheckData();
    }

    @Test
    void testCheckIsInterface() {
        //Arrange
        //Act
        //Assert
        assertThat(Check.class)
                .isInterface();
    }

    @Test
    void testErrorDataCheck()
    {
        //Arrange
        String[] error_data = new String[]
                {
                    "firstname: 1233",
                        "lastname: 132"
                };
        //Act
        //Assert
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() ->{ check.checkDataEmployees(error_data);})
                .withMessage("Invalid data format");
    }

    @Test
    void testOkDataCheck() {
        //Arrange
        String[] ok_data = new String[]
                {
                        "id: "+UUID.randomUUID().toString() + "\n" +
                                "firstName: " + RandomString.make(8) + "\n" +
                                "lastName: " + RandomString.make(7) + "\n" +
                                "description: " + RandomString.make(7) + "\n" +
                                "postId: " + UUID.randomUUID().toString()+"\n" +
                                "characteristics: " + RandomString.make(7) + "\n" +
                                "image: none",
                        "id: "+UUID.randomUUID().toString() + "\n" +
                                "firstName: " + RandomString.make(8) + "\n" +
                                "lastName: " + RandomString.make(7) + "\n" +
                                "description: " + RandomString.make(7) + "\n" +
                                "postId: " + UUID.randomUUID().toString()+"\n" +
                                "characteristics: " + RandomString.make(7) + "\n" +
                                "image: none"
                };
        //Act
        //Assert
        assertThat(check.checkDataEmployees(ok_data)).isTrue();
    }

    @Test
    void testErrorFilePathCheck() {
        //Arrange

        String random_file = RandomString.make(7)+".txt";
        Path path = Paths.get("C:\\",random_file);
        //Act
        boolean result = check.checkSourcePathData(path);
        //Assert
        assertThat(result).isFalse();
    }
    @Test
    void testOkFilePathCheck(@TempDir Path dir) throws IOException {
        //Arrange
        Path file = Paths.get(dir.toString(),RandomString.make(7)+".txt");
        FileWriter fileWriter = new FileWriter(file.toString());
        fileWriter.write("test");
        fileWriter.close();
        //Act
        boolean result = check.checkSourcePathData(file);
        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void testOkDataPostsFileCheck() {
        //Arrange
        String[] data = new String[]
                {
                        UUID.randomUUID().toString() + ": " + RandomString.make(8),
                        UUID.randomUUID().toString() + ": " + RandomString.make(8),
                        UUID.randomUUID().toString() + ": " + RandomString.make(8)
                };
        //Act
        boolean result = check.checkDataPosts(data);
        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void testErrorDataPostsFileCheck() {
        //Arrange
        String[] data = new String[]
                {
                        RandomString.make(8),
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString() + ": " + RandomString.make(8)
                };
        //Act
        //Assert
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() ->{ check.checkDataPosts(data);})
                .withMessage("Invalid post data format");
    }

    @Test
    void testCheckOkPathFile(@TempDir Path dir) {
        //Arrange
        String file = Paths.get(dir.toString(),RandomString.make(7)+".txt").toString();
        //Act
        //Assert
        assertThat(check.checkFormatFilePath(file))
                .isTrue();

    }

    @Test
    void testCheckErrorPathFile(@TempDir Path dir) {
        //Arrange
        String file = dir.toString();
        //Act
        //Assert
        assertThat(check.checkFormatFilePath(file))
                .isFalse();

    }

    @Test
    void testCheckErrorPathDirectory(@TempDir Path dir) {
        //Arrange
        String file = Paths.get(dir.toString(),RandomString.make(7)+".txt").toString();
        //Act
        //Assert
        assertThat(check.checkFormatDirectoryPath(file))
                .isFalse();

    }

    @Test
    void testCheckOkPathDirectory(@TempDir Path dir) {
        //Arrange
        String file = dir.toString();
        //Act
        //Assert
        assertThat(check.checkFormatDirectoryPath(file))
                .isTrue();

    }
}
