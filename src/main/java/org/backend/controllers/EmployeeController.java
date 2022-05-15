package org.backend.controllers;

import org.backend.data.DataLoader;
import org.backend.data.DataSaver;
import org.backend.employee.Employee;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter(AccessLevel.PRIVATE)
public class EmployeeController extends BaseController<Employee> {

    public EmployeeController(@NonNull DataSaver dataSaver, @NonNull DataLoader dataLoader, Path file) {
        super(dataSaver,dataLoader,file);
    }

    @Override
    void Init()
    {
        List<Employee> employees = List.of(dataLoader.loadEmployeesData(file));
        setAllObjects(employees.stream().collect(Collectors.toMap(Employee::getId, Function.identity())));
    }



}
