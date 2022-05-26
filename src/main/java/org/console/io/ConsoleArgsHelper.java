package org.console.io;

import lombok.Getter;
import org.backend.Main;
import org.backend.utils.Check;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class ConsoleArgsHelper {
    Path pathPosts;
    Path pathEmployees;
    boolean isJson = false;
    public ConsoleArgsHelper(String[] args)
    {
        Check check = Main.check;
        String path = args[0];
        if (check.checkFormatFilePath(path)) {
            if (args.length <= 1) {
                pathEmployees = Paths.get(path);
                pathPosts = Paths.get(pathEmployees.getParent().toString(), "posts.txt");
            } else {
                if (args[1].equals("-p")) {
                    pathPosts = Paths.get(path);
                    pathEmployees = Paths.get(pathPosts.getParent().toString(), "employees.txt");
                } else {
                    pathEmployees = Paths.get(path);
                    pathPosts = Paths.get(pathEmployees.getParent().toString(), "posts.txt");
                }
            }
        }
        else if (check.checkFormatDirectoryPath(path)) {
            pathPosts = Paths.get(path, "posts.txt");
            pathEmployees = Paths.get(path, "employees.txt");
        } else {

            path = Paths.get("data").toAbsolutePath().toString();
            Main.output.errorPath(path);
            pathPosts = Paths.get(path, "posts.txt");
            pathEmployees = Paths.get(path, "employees.txt");

        }

        for(String arg: args)
        {
            if(arg.equals("-j"))
            {
                isJson = true;
            }
        }
    }
}
