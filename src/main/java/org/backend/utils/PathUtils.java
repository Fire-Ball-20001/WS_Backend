package org.backend.utils;

import org.backend.config.Config;
import org.backend.controllers.BaseControllerMethods;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

public class PathUtils {
    public boolean isFile(File file)
    {
        if(file.exists())
        {
            return file.isFile();
        }
        else
        {
            PathMatcher matcher = file.toPath().getFileSystem().getPathMatcher(Config.FILE_GLOB_EXTENSION);
            return matcher.matches(file.toPath().getFileName());
        }
    }
    public boolean isDirectory(File file)
    {
        if(file.exists())
        {
            return file.isDirectory();
        }
        else
        {
            PathMatcher matcher = file.toPath().getFileSystem().getPathMatcher(Config.DIRECTORY_GLOB_EXTENSION);
            return matcher.matches(file.toPath().getFileName());
        }
    }
    public File FormatPathIfFileIsDirectory(Path path, BaseControllerMethods<?> controller)
    {
        File file = new File(path.toString());
        if(!isFile(file))
        {
            String name_file = controller.getDefaultFileName();
            return new File(path.toString(),name_file);
        }
        else
        {
            return file;
        }
    }
}
