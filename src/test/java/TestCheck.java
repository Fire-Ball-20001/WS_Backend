
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public class TestCheck {
    Check check;
    String[] error_data = new String[]
            {

            };
    String[] ok_data = new String[]
            {

            };

    @BeforeEach
    void BeforeEach() {
        check = new CheckData();
    }


    @Test
    void testErrorDataCheck()
    {
        //Arrange
        //Act
        //Assert
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() ->{ check.checkDataEmployees(error_data);})
                .withMessage("Invalid data format");
    }

    @Test
    void testOkDataCheck() {
        //Arrange
        //Act
        //Assert
        assertThat(check.checkDataEmployees(ok_data)).isTrue();
    }

    @Test
    void testErrorFilePathCheck() {
        //Arrange

        String random_file = RandomString.make(7)+".txt";
        Path path = Path.of("C:\\",random_file);
        //Act
        boolean result = check.checkSourcePathData(path);
        //Assert
        assertThat(result).isFalse();
    }
    @Test
    void testOkFilePathCheck(@TempDir Path dir) throws IOException {
        //Arrange
        Path file = Path.of(dir.toString(),RandomString.make(7)+".txt");
        FileWriter fileWriter = new FileWriter(file.toString());
        fileWriter.write("test");
        fileWriter.close();
        //Act
        boolean result = check.checkSourcePathData(file);
        //Assert
        assertThat(result).isFalse();
    }


}
