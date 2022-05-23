package org.backend.files;

import org.backend.Main;
import org.backend.data.DataBuilder;
import org.backend.data.DataSaverAndLoader;
import org.backend.models.BaseMethodsForEmployeeAndPost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.backend.models.Employee;
import org.backend.models.PostEmployee;
import org.backend.utils.Check;
import org.backend.utils.PathUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
public class FileSaverAndLoader implements DataSaverAndLoader {
    @NonNull
    Check check;
    @NonNull
    private DataBuilder dataBuilder;



    @Override
    public void createOrReplaceSaveData(BaseMethodsForEmployeeAndPost[] objects, Path path_file) {
        if(objects.length==0)
        {
            deleteFileOrDirectory(new File(path_file.toString()));
            return;
        }
        PathUtils utils = new PathUtils();
        File file = utils.FormatPathIfFileIsDirectory(path_file,objects[0].getBaseController());
        if(check.checkSourcePathData(path_file))
        {
            if(!deleteFileOrDirectory(file))
            {
                throw new RuntimeException("Failed to save file");
            }
        }

        createDirectories(file);
        try(FileWriter fileWriter = new FileWriter(file))
        {
            fileWriter.write(dataBuilder.getSaveString(objects));
            fileWriter.flush();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed to save file");
        }

    }

    @Override
    public Employee[] loadEmployeesData(Path path_file) {
        //Get file
        PathUtils utils = new PathUtils();
        File file = utils.FormatPathIfFileIsDirectory(path_file, Main.employeeController.getBaseController());
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

    private boolean deleteFileOrDirectory(File file)
    {
        if(file.isDirectory())
        {
            for (File file_in_directory:
                     file.listFiles()) {
                if(!deleteFileOrDirectory(file_in_directory))
                {
                    return false;
                }
            }
        }
        else
        {
            try {
                if(!file.delete())
                {
                    return false;
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    private void createDirectories(File file)
    {
        new File(file.getParent()).mkdirs();
    }
}
