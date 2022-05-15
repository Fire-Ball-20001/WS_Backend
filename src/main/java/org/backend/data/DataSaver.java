package org.backend.data;

import org.backend.employee.BaseMethodsForEmployeeAndPost;

import java.nio.file.Path;

public interface DataSaver extends BaseDataMethods  {
    void createOrReplaceSaveData(BaseMethodsForEmployeeAndPost[] employees, Path file);
    void setDataBuilder(DataBuilder dataBuilder);
    DataBuilder getDataBuilder();
}
