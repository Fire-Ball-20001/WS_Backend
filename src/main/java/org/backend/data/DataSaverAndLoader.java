package org.backend.data;

import org.backend.employee.BaseMethodsForEmployeeAndPost;
import org.backend.employee.Employee;
import org.backend.employee.PostEmployee;

import java.nio.file.Path;

public interface DataSaverAndLoader extends BaseDataMethods  {
    void createOrReplaceSaveData(BaseMethodsForEmployeeAndPost[] employees, Path file);
    Employee[] loadEmployeesData(Path file);
    PostEmployee[] loadPostsData(Path file);
    void setDataBuilder(DataBuilder dataBuilder);
    DataBuilder getDataBuilder();
}
