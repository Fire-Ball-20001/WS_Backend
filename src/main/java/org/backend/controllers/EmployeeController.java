package org.backend.controllers;

import org.backend.Main;
import org.backend.config.Config;
import org.backend.data.DataLoader;
import org.backend.data.DataSaver;
import org.backend.employee.Employee;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.backend.io.outputs.ConsoleOutput;
import org.backend.observer.IListener;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter(AccessLevel.PRIVATE)
public class EmployeeController extends BaseController<Employee> implements IListener {

    public EmployeeController(@NonNull DataSaver dataSaver, @NonNull DataLoader dataLoader, Path file) {
        super(dataSaver, dataLoader, file);
        Main.observer.addListener(this);
    }

    @Override
    public void Init() {
        Employee [] employees_array;
        try {
            employees_array = dataLoader.loadEmployeesData(file);
        }
        catch (RuntimeException e)
        {
            Main.output.errorLoadEmployee();
            employees_array = new Employee[0];
        }
        List<Employee> employees = List.of(employees_array);
        setAllObjects(
                employees
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        Employee::getId,
                                        Function.identity())));

    }


    @Override
    public String getDefaultFileName() {
        return Config.EMPLOYEE_FILE_NAME;
    }

    @Override
    public List<Employee> getSortObjects()
    {
        return getAllObjects().stream().sorted().collect(Collectors.toList());
    }

    @Override
    public void update() {
        List<Employee> employeeList = new ArrayList<>();
        for (Employee employee : getAllObjects()) {
            try {
                Main.postController.getObjectById(employee.getId());
            } catch (RuntimeException e) {
                employee.setPost(Main.postController.getDefaultPost());
            } finally {
                employeeList.add(employee);
            }
        }
        setAllObjects(
                employeeList
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        Employee::getId,
                                        Function.identity())));
    }
}
