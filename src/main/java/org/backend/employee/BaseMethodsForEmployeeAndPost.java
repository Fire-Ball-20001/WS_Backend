package org.backend.employee;

import java.util.UUID;

public interface BaseMethodsForEmployeeAndPost {
    UUID getId();
    String getName();
    String toSaveString();
}
