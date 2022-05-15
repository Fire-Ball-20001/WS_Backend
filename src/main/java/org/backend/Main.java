package org.backend;

import org.backend.controllers.EmployeeController;
import org.backend.controllers.PostController;
import org.backend.data.DataBuilder;
import org.backend.data.DataLoader;
import org.backend.data.DataSaver;
import org.backend.files.FileDataBuilder;
import org.backend.files.FileLoader;
import org.backend.files.FileSaver;
import org.backend.utils.CheckData;

import java.nio.file.Path;

public class Main {
    public static PostController postController;
    public static EmployeeController employeeController;
    public static void main(String[] args)
    {
        Path file = Path.of(args[0]);
        DataBuilder dataBuilder = new FileDataBuilder(new CheckData());
        DataSaver dataSaver = new FileSaver(new CheckData(),dataBuilder);
        DataLoader dataLoader = new FileLoader(new CheckData(),dataBuilder);
        postController = new PostController(dataSaver,dataLoader,file);
    }
}
