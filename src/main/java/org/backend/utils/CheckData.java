package org.backend.utils;

import org.backend.config.Config;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class CheckData implements Check {
    @Override
    public boolean checkDataEmployees(String[] data) {
        Pattern pattern = Pattern.compile(Config.REGEX_EMPLOYEE);
        for(String one_employee : data)
        {
            if(!pattern.matcher(one_employee).matches())
            {
                throw new RuntimeException("Invalid data format");
            }
        }
        return true;
    }

    @Override
    public boolean checkSourcePathData(Path path) {
        File file = new File(path.toString());
        if(file.exists() && (file.isFile() || file.isDirectory()))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkFormatFilePath(String path) {
        Path good_path;
        try
        {
            good_path = Paths.get(path);
        }
        catch (Exception e)
        {
            return false;
        }
        if(!new PathUtils().isFile(new File(good_path.toString())))
        {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkFormatDirectoryPath(String path) {
        Path good_path;
        try
        {
            good_path = Paths.get(path);
        }
        catch (Exception e)
        {
            return false;
        }
        if(new PathUtils().isFile(new File(good_path.toString())))
        {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkDataPosts(String[] data) {
        Pattern pattern = Pattern.compile(Config.REGEX_POST);
        for (String one_post:
             data) {
            if(!pattern.matcher(one_post).matches())
            {
                throw new RuntimeException("Invalid post data format");
            }
        }
        return true;
    }
}
