package org.backend.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class PostEmployee implements BaseMethodsForEmployeeAndPost {
    final UUID id;
    final String name;

    @Override
    public String toSaveString() {
        return id+": "+name;
    }
}
