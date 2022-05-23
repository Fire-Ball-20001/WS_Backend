package org.backend.data;

import org.backend.models.BaseMethodsForEmployeeAndPost;
import org.backend.models.Employee;
import org.backend.models.PostEmployee;

import java.nio.file.Path;

public interface DataSaverAndLoader extends BaseDataMethods  {
    void createOrReplaceSaveData(BaseMethodsForEmployeeAndPost[] employees, Path file);
    Employee[] loadEmployeesData(Path file);
    PostEmployee[] loadPostsData(Path file);
    void setDataBuilder(DataBuilder dataBuilder);
    DataBuilder getDataBuilder();
}
