package org.backend.files;

import org.backend.data.DataBuilder;
import org.backend.data.DataLoader;
import org.backend.employee.Employee;
import org.backend.employee.PostEmployee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.backend.utils.Check;

import java.nio.file.Path;

@AllArgsConstructor
public class FileLoader implements DataLoader {
    @Getter
    @Setter
    @NonNull
    Check check;
    @Getter
    @Setter
    @NonNull
    private DataBuilder dataBuilder;



    @Override
    public Employee[] loadEmployeesData(Path file) {
        return new Employee[0];
    }

    @Override
    public PostEmployee[] loadPostsData(Path file) {
        return new PostEmployee[0];
    }

}
