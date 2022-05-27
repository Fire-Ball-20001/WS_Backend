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
    boolean isAuto = false;
    String findName = "";
    String filterPostId = "";
    public ConsoleArgsHelper(String[] args)
    {
        Check check = Main.check;
        if(args.length <1)
        {
            throw new RuntimeException("ERROR: Нужен хотя бы один аргумент");
        }
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

        for(int i =0;i<args.length;i++)
        {
            if(args[i].equals("-j"))
            {
                isJson = true;
            }
            else if(args[i].equals("-f"))
            {
                isAuto = true;
                findName = args[i+1];
                i++;
            }
            else if(args[i].equals("-postId"))
            {
                filterPostId = args[i+1];
            }
        }
    }
}
