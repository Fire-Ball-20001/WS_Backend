import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args)
    {
        Path path = FileSystems.getDefault().getPath("C:\\Test");
        Path path_2 = FileSystems.getDefault().getPath("C:\\Test");

        System.out.println(Path.of(path.toString(),"test.txt"));
    }
}
