package org.backend.data;

import org.backend.employee.Employee;
import org.backend.employee.PostEmployee;

import java.nio.file.Path;

public interface DataLoader extends BaseDataMethods {

    Employee[] loadEmployeesData(Path file);
    PostEmployee[] loadPostsData(Path file);
    void setDataBuilder(DataBuilder dataBuilder);
    DataBuilder getDataBuilder();
}
