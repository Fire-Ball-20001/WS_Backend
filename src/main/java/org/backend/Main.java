package org.backend;

import com.google.gson.Gson;
import org.backend.controllers.EmployeeController;
import org.backend.controllers.PostController;
import org.backend.data.DataBuilder;
import org.backend.data.DataSaverAndLoader;
import org.backend.files.FileDataBuilder;
import org.backend.files.FileSaverAndLoader;
import org.console.io.ConsoleArgsHelper;
import org.console.io.ConsoleBuilder.Menu;
import org.console.io.ConsoleBuilder.MenuBuilder;
import org.console.io.inputs.ConsoleInput;
import org.console.io.outputs.ConsoleOutput;
import org.backend.json.JsonBuilder;
import org.backend.observer.Observer;
import org.backend.utils.Check;
import org.backend.utils.CheckData;

public class Main {
    //Temporary solution
    //------------------------------------------
    public static PostController postController;
    public static EmployeeController employeeController;
    public static Observer observer;
    public static ConsoleInput input;
    public static ConsoleOutput output;
    public static Menu mainMenu;
    public static Check check;
    public static Gson gson;
    private static ConsoleArgsHelper consoleArgsHelper;
    //-----------------------------------------------------

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
        gson = new Gson();
        consoleArgsHelper = new ConsoleArgsHelper(args);
        DataBuilder dataBuilder;
        if(consoleArgsHelper.isJson())
        {
            dataBuilder = new JsonBuilder(new CheckData());
        }
        else
        {
            dataBuilder = new FileDataBuilder(new CheckData());
        }
        DataSaverAndLoader dataSaverAndLoader = new FileSaverAndLoader(new CheckData(), dataBuilder);
        postController = new PostController(dataSaverAndLoader, consoleArgsHelper.getPathPosts());

        postController.Init();
        employeeController = new EmployeeController(dataSaverAndLoader, consoleArgsHelper.getPathEmployees());
        employeeController.Init();
        Main.output.outputCountEmployeesAndPosts();
        mainMenu.showMenu();
        employeeController.saveData();
        postController.saveData();
    }
}
