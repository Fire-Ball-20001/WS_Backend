package org.backend.controllers;

import org.backend.Main;
import org.backend.config.Config;
import org.backend.data.DataSaverAndLoader;
import org.backend.models.Employee;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.backend.models.PostEmployee;
import org.backend.observer.IListener;
import org.backend.utils.FindArgument;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter(AccessLevel.PRIVATE)
public class EmployeeController extends BaseController<Employee> implements IListener {

    public EmployeeController(@NonNull DataSaverAndLoader dataSaverAndLoader, Path file) {
        super(dataSaverAndLoader, file);
        Main.observer.addListener(this);
    }

    @Override
    public void Init() {
        Employee [] employees_array;
        try {
            employees_array = dataSaverAndLoader.loadEmployeesData(file);
        }
        catch (RuntimeException e)
        {
            Main.output.errorLoadEmployee();
            employees_array = new Employee[0];
        }
        List<Employee> employees = Arrays.asList(employees_array);

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

    //Algorin

    public UUID[] findFilteredEmployeesByPost(FindArgument argument, PostEmployee postEmployee)
    {
        UUID[] employees = super.findByFindArg(argument);
        if(postEmployee == null)
        {
            return employees;
        }
        List<UUID> result = new ArrayList<>();
        for (UUID id:
             employees) {
            if(getObjectById(id).getPost().equals(postEmployee))
            {
                result.add(id);
            }
        }
        return result.toArray(new UUID[0]);
    }

    public UUID[] findEmployeesByPost(PostEmployee postEmployee) {
        return findFilteredEmployeesByPost(FindArgument.builder().build(),postEmployee);
    }

    public UUID[] findEmployeesByRoughPostId(String postId) {
        List<UUID> employees = new ArrayList<>();
        UUID[] posts = Main.postController.getRoughUUIDs(postId);
        for(UUID finds_post : posts)
        {
            employees.addAll(
                    Arrays.stream(
                            findEmployeesByPost(Main.postController.getObjectById(finds_post))).collect(Collectors.toSet()));
        }
        return employees.toArray(new UUID[0]);
    }

}
