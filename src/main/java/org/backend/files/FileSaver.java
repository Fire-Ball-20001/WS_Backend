package org.backend.files;

import org.backend.data.DataBuilder;
import org.backend.data.DataSaver;
import org.backend.employee.BaseMethodsForEmployeeAndPost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.backend.utils.Check;

import java.nio.file.Path;

@AllArgsConstructor
public class FileSaver implements DataSaver {
    @Getter
    @Setter
    @NonNull
    Check check;
    @Getter
    @Setter
    @NonNull
    private DataBuilder dataBuilder;



    @Override
    public void createOrReplaceSaveData(BaseMethodsForEmployeeAndPost[] employees, Path file) {

    }
}
