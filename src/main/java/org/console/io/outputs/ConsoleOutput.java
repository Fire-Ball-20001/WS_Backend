package org.console.io.outputs;

import org.backend.Main;
import org.backend.models.Employee;
import org.backend.models.PostEmployee;

public class ConsoleOutput {
    public static void textListCommands(Void object) {
        System.out.println("Список команд:");
    }

    public static Void outputEmployee(Void object) {
        Employee[] employees = Main.employeeController.getSortObjects().toArray(new Employee[0]);
        if (employees.length == 0) {
            System.out.println("Участников нет.");
            return null;
        }
        System.out.println("Список участников: ");
        for (int i = 1; i <= employees.length; i++) {
            System.out.println(employees[i - 1]);
            System.out.println("-------------------" + i + "/" + employees.length + "----------------------------");
            waitEnter();
        }
        return null;
    }

    public static void enterCommand() {
        System.out.print("Введите номер команды: ");
    }

    public static void outputErrorEnterCommand() {
        System.out.println("Неверный номер команды!");
    }

    public static void newLine() {
        System.out.println();
    }

    public static void outputTextNL(String object) {
        System.out.println(object);
    }

    public static void outputText(String object) {
        System.out.print(object);
    }

    public static void errorLoadPost() {
        System.out.println("Ошибка загрузки должностей! Использование параметров по умолчанию.");
    }

    public static void errorLoadEmployee() {
        System.out.println("Ошибка загрузки участников! Использование параметров по умолчанию.");
    }

    public static void errorPath(String path) {
        System.out.println("Неверный путь. Использован путь по умолчанию: " + path);
    }

    public static Void outputPost(Void object) {
        outputPost(null, false);
        return null;
    }

    public static Void outputPost(Void object, boolean no_wait) {
        PostEmployee[] postEmployees = Main.postController.getSortObjects().toArray(new PostEmployee[0]);
        if (postEmployees.length == 0) {
            System.out.println("Должности отсутсвуют.");
            return null;
        }
        System.out.println("Список должностей: ");

        for (int i = 1; i <= postEmployees.length; i++) {
            System.out.println(i + ". " + postEmployees[i - 1] + ";");
        }
        if (!no_wait) {
            waitEnter();
        }
        return null;
    }

    public static void waitEnter() {
        System.out.print("Нажмите Enter, чтобы продолжить...");
        try {
            System.in.read();
        } catch (Exception e) {
            System.out.println();
        }
    }

    public static void selectPost() {
        System.out.println("Выберите должность из предложенных");
        outputPost(null,true);
    }
    public static void errorVoidString() {
        System.out.println("Поле не должно быть пустым!");
    }

    public static void outputCountEmployeesAndPosts(){
        System.out.println("Количество участников: " + Main.employeeController.getCountObjects());
        System.out.println("Количество должностей: " + Main.postController.getCountObjects());
    }
    public static void errorFindObject(){
        System.out.println("Не найден объект!");
    }
    public static void errorNoPosts(){
        System.out.println("Не обнаруженно должностей, продолжение невозможно.");
    }
}
