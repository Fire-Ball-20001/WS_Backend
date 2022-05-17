package org.backend.files;

import org.backend.Main;
import org.backend.config.Config;
import org.backend.data.DataBuilder;
import org.backend.data.DataLoader;
import org.backend.employee.Employee;
import org.backend.employee.PostEmployee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.backend.utils.Check;
import org.backend.utils.PathUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
@NonNull
public class FileLoader implements DataLoader {
    Check check;
    private DataBuilder dataBuilder;

    @Override
    public Employee[] loadEmployeesData(Path path_file) {
        //Get file
        PathUtils utils = new PathUtils();
        File file = utils.FormatPathIfFileIsDirectory(path_file,Main.employeeController.getBaseController());
        if(!check.checkSourcePathData(file.toPath()))
        {
            throw new RuntimeException("Error loading data");
        }
        //Read file
        String employees_string;
        try(BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            employees_string = fileReader.lines().collect(Collectors.joining("\n"));
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error loading data");
        }

        //File Processing
        try {
            return dataBuilder.parseEmployees(employees_string);
        }
        catch (RuntimeException exception)
        {
            throw new RuntimeException("Invalid employee data format");
        }
    }

    @Override
    public PostEmployee[] loadPostsData(Path path_file) {
        //Get file
        PathUtils utils = new PathUtils();
        File file = utils.FormatPathIfFileIsDirectory(path_file,Main.postController.getBaseController());
        if(!check.checkSourcePathData(file.toPath()))
        {
            throw new RuntimeException("Error loading data");
        }

        //Read file
        String postEmployees_string;
        try(BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            postEmployees_string = fileReader.lines().collect(Collectors.joining("\n"));
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error loading data");
        }

        //File Processing
        try {
            return dataBuilder.parsePostEmployees(postEmployees_string);
        }
        catch (RuntimeException exception)
        {
            throw new RuntimeException("Invalid post data format");
        }
    }

}
