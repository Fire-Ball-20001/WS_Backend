package org.backend.io.inputs;


import org.backend.Main;
import org.backend.models.Employee;
import org.backend.models.PostEmployee;
import org.backend.utils.FindArgument;

import java.util.Scanner;
import java.util.UUID;

public class ConsoleInput {

    static Scanner input = new Scanner(System.in);
    public ConsoleInput.ConsoleInputCommon common;
    public ConsoleInput()
    {
        common = new ConsoleInputCommon();
    }

    public static class ConsoleInputStatic
    {
        public static Void addEmployee(Void object){
            String firstName = enterString(true,"Введите имя: ");
            String lastName = enterString(true,"Введите фамилию: ");
            String description = enterString(false,"Введите описание: ");
            String characteristics_str = enterString(true,"Введите характеристики, через пробел: ",true);
            String[] characteristics = characteristics_str.split("\\s");
            Main.output.selectPost();
            int post_number = enterNumberConsole(Main.postController.getCountObjects(),
                    "Неверный номер должности!",
                    "Введите номер должности: ")-1;
            PostEmployee post = Main.postController.getSortObjects().get(post_number);
            Employee employee = Employee.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .description(description)
                    .characteristics(characteristics)
                    .post(post)
                    .build();
            Main.employeeController.addObject(employee);
            return null;
        }
        public static Void setEmployee(Void object){
            Employee employee = findEmployee();
            if(employee == null)
            {
                return null;
            }
            Main.output.outputTextNL("Список параметров: ");
            Main.output.outputTextNL("1. Имя");
            Main.output.outputTextNL("2. Фамилия");
            Main.output.outputTextNL("3. Описание");
            Main.output.outputTextNL("4. Должность");
            Main.output.outputTextNL("5. Характеристики");
            int option_id = enterNumberConsole(5,
                    "Неверный номер параметра!",
                    "Введите номер параметра: ")-1;

            switch (option_id)
            {
                case 0:
                {
                    String new_value = enterString(false,"Введите новое имя: ");
                    employee.setFirstName(new_value);
                    break;
                }
                case 1:
                {
                    String new_value = enterString(false,"Введите новую фамилию: ");
                    employee.setLastName(new_value);
                    break;
                }
                case 2:
                {
                    String new_value = enterString(false,"Введите новое описание: ");
                    employee.setDescription(new_value);
                    break;
                }
                case 3:
                {
                    if(Main.postController.getCountObjects() == 0)
                    {
                        Main.output.errorNoPosts();
                        return null;
                    }
                    Main.output.selectPost();
                    int post_number = enterNumberConsole(Main.postController.getCountObjects(),
                            "Неверный номер объекта!",
                            "Введите номер новой должности: ")-1;
                    employee.setPost(Main.postController.getSortObjects().get(post_number));
                    break;
                }
                case 4:
                {
                    String characteristics_str = enterString(true,
                            "Введите новые характеристики, через пробел: ",true);
                    String[] characteristics = characteristics_str.split("\\s");
                    employee.setCharacteristics(characteristics);
                }
            }
            Main.employeeController.setObject(employee);
            return null;
        }
        public static Void deleteEmployee(Void object){
            Employee employee = findEmployee();
            if(employee == null)
            {
                return null;
            }
            Main.employeeController.deleteObjectById(employee.getId());
            return null;
        }
        public static Boolean isActiveButtonsEmployees(Void object){
            if(Main.employeeController.getCountObjects()>0)
            {
                return Boolean.TRUE;
            }
            else
            {
                return Boolean.FALSE;
            }
        }
        public static Void addPost(Void object){
            String name = enterString(true,"Введите имя: ");
            Main.postController.addObject(new PostEmployee(UUID.randomUUID(),name));
            return null;
        }
        public static Void deletePost(Void object){
            Main.output.selectPost();
            int post_number = enterNumberConsole(Main.postController.getCountObjects(),
                    "Неверный номер",
                    "Введите номер должности: ")-1;
            PostEmployee employee = Main.postController.getSortObjects().get(post_number);
            Main.postController.deleteObjectById(employee.getId());
            return null;
        }
        public static Boolean isActiveButtonsPost(Void object){
            if(Main.postController.getCountObjects()>0)
            {
                return Boolean.TRUE;
            }
            else
            {
                return Boolean.FALSE;
            }
        }

    }

    public class ConsoleInputCommon
    {

        public int enterCommand(int max_command) {
            return enterNumberConsole(max_command);
        }
    }

    static int enterNumberConsole(int max_command, String text_error, String text_enter)
    {

        int command = -1;
        Main.output.outputText(text_enter);

        while (input.hasNext())
        {

            if(input.hasNextInt()) {
                command = input.nextInt();
                if (command < 0 || command > max_command) {
                    Main.output.outputTextNL(text_error);
                    Main.output.outputText(text_enter);
                } else {
                    break;
                }
            }
            else {
                input.next();
                Main.output.outputTextNL(text_error);
                Main.output.outputText(text_enter);
            }
        }
        return command;
    }
    static int enterNumberConsole(int max_command)
    {
        return enterNumberConsole(max_command,"Неверный номер команды!","Введите номер команды: ");
    }

    static String enterString(boolean void_enabled,String text,boolean is_array)
    {
        Main.output.outputText(text);
        while (input.hasNext())
        {
            String input_str ="";
            if(is_array)
            {
                input.nextLine();
                input_str = input.nextLine();
            }
            else
            {
                input_str = input.next();
            }
            if(void_enabled)
            {
                if(input_str.isEmpty())
                {
                    Main.output.errorVoidString();
                    Main.output.outputText(text);
                    continue;
                }
                else
                {
                    return input_str;
                }
            }
            else
            {
                return input_str;
            }
        }
        return "";
    }
    static String enterString(boolean void_enabled,String text)
    {
        return enterString(void_enabled,text,false);
    }

    static Employee findEmployee()
    {
        Employee employee;
        String name = enterString(true,"Введите имя и фамилию и должность, при желании, через пробел: ",true);
        String[] strings = name.split(" ");
        FindArgument argument;
        if(strings.length >= 3)
        {
            argument = FindArgument.builder()
                    .name(strings[0]+" "+strings[1])
                    .postName(strings[2])
                    .build();
        }
        else
        {
            argument = FindArgument.builder()
                    .name(String.join(" ", strings))
                    .build();
        }
        UUID[] uuids = Main.employeeController.findIdsByName(argument);
        if(uuids.length > 1)
        {
            Main.output.outputTextNL("Обнаруженно несколько совпадений, какое из них выбрать?");
            int i=1;
            for(UUID uuid : uuids)
            {
                Employee tempEmployee = Main.employeeController.getObjectById(uuid);
                Main.output.outputTextNL(i + ". Описание: "+tempEmployee.getDescription() +
                        "\n\t Должность: "+tempEmployee.getPost().getName()+";");
                i++;
            }
            int temp_id = enterNumberConsole(uuids.length,
                    "Неверный номер участника!",
                    "Введите выбранный номер: ");
            employee = Main.employeeController.getObjectById(uuids[temp_id]);
        }
        else if(uuids.length == 1)
        {
            employee = Main.employeeController.getObjectById(uuids[0]);
        }
        else
        {
            Main.output.errorFindObject();
            return null;
        }
        return employee;
    }
}
