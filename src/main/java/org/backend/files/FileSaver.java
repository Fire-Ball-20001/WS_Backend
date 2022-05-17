package org.backend.files;

import org.backend.config.Config;
import org.backend.data.DataBuilder;
import org.backend.data.DataSaver;
import org.backend.employee.BaseMethodsForEmployeeAndPost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.backend.employee.Employee;
import org.backend.employee.PostEmployee;
import org.backend.utils.Check;
import org.backend.utils.PathUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

@AllArgsConstructor
public class FileSaver implements DataSaver {
    @Getter
    @Setter
    @NonNull
    Check check;
    @Getter
    @Setter
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
