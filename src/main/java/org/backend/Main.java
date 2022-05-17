package org.backend;

import org.backend.controllers.EmployeeController;
import org.backend.controllers.PostController;
import org.backend.data.DataBuilder;
import org.backend.data.DataLoader;
import org.backend.data.DataSaver;
import org.backend.files.FileDataBuilder;
import org.backend.files.FileLoader;
import org.backend.files.FileSaver;
import org.backend.io.ConsoleBuilder.Menu;
import org.backend.io.ConsoleBuilder.MenuBuilder;
import org.backend.io.inputs.ConsoleInput;
import org.backend.io.outputs.ConsoleOutput;
import org.backend.observer.Observer;
import org.backend.utils.Check;
import org.backend.utils.CheckData;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static PostController postController;
    public static EmployeeController employeeController;
    public static Observer observer;
    public static ConsoleInput input;
    public static ConsoleOutput output;
    public static Menu mainMenu;
    public static Check check;

    public static void main(String[] args) {
        mainMenu = MenuBuilder.createMenu()
                .addMenu()
                .setText("Управление участниками")
                .addButton()
                .textButton("Вывести список участников")
                .activateFunction(ConsoleOutput::outputEmployee)
                .build()
                .addButton()
                .textButton("Добавить нового участника")
                .activateFunction(ConsoleInput.ConsoleInputStatic::addEmployee)
                .build()
                .addButton()
                .textButton("Изменить участника")
                .isActiveFunction(ConsoleInput.ConsoleInputStatic::isActiveButtonsEmployees)
                .activateFunction(ConsoleInput.ConsoleInputStatic::setEmployee)
                .build()
                .addButton()
                .textButton("Удалить участника")
                .isActiveFunction(ConsoleInput.ConsoleInputStatic::isActiveButtonsEmployees)
                .activateFunction(ConsoleInput.ConsoleInputStatic::deleteEmployee)
                .build()
                .addButton()
                .textButton("Назад")
                .isBack(true)
                .build()
                .build()
                .addMenu()
                .setText("Управление должностями")
                .addButton()
                .textButton("Вывести все должности")
                .activateFunction(ConsoleOutput::outputPost)
                .build()
                .addButton()
                .textButton("Добавить новую должность")
                .activateFunction(ConsoleInput.ConsoleInputStatic::addPost)
                .build()
                .addButton()
                .textButton("Удалить должность")
                .isActiveFunction(ConsoleInput.ConsoleInputStatic::isActiveButtonsPost)
                .activateFunction(ConsoleInput.ConsoleInputStatic::deletePost)
                .build()
                .addButton()
                .textButton("Назад")
                .isBack(true)
                .build()
                .build()
                .addButton()
                .textButton("Выход")
                .isExit(true)
                .build()
                .buildMenu();


        input = new ConsoleInput();
        output = new ConsoleOutput();
        check = new CheckData();
        observer = new Observer();
        String path = args[0];
        Path path_posts;
        Path path_employees;
        if (check.checkFormatFilePath(path)) {
            if (args.length <= 1) {
                path_employees = Paths.get(path);
                path_posts = Paths.get(path_employees.getParent().toString(), "posts.txt");
            } else {
                if (args[1].equals("-p")) {
                    path_posts = Paths.get(path);
                    path_employees = Paths.get(path_posts.getParent().toString(), "employees.txt");
                } else {
                    path_employees = Paths.get(path);
                    path_posts = Paths.get(path_employees.getParent().toString(), "posts.txt");
                }
            }
        }
        else if (check.checkFormatDirectoryPath(path)) {
            path_posts = Paths.get(path, "posts.txt");
            path_employees = Paths.get(path, "employees.txt");
        } else {

            path = Paths.get("data").toAbsolutePath().toString();
            output.errorPath(path);
            path_posts = Paths.get(path, "posts.txt");
            path_employees = Paths.get(path, "employees.txt");

        }
        DataBuilder dataBuilder = new FileDataBuilder(new CheckData());
        DataSaver dataSaver = new FileSaver(new CheckData(), dataBuilder);
        DataLoader dataLoader = new FileLoader(new CheckData(), dataBuilder);
        postController = new PostController(dataSaver, dataLoader, path_posts);

        postController.Init();
        employeeController = new EmployeeController(dataSaver, dataLoader, path_employees);
        employeeController.Init();
        Main.output.outputCountEmployeesAndPosts();
        mainMenu.showMenu();
        employeeController.saveData();
        postController.saveData();
    }
}
