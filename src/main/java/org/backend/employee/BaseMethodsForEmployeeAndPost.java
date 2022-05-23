package org.backend.employee;

import org.backend.controllers.BaseController;
import org.backend.controllers.BaseControllerMethods;

import java.util.UUID;

public interface BaseMethodsForEmployeeAndPost {
    UUID getId();
    String getName();
    String toSaveString();
    BaseControllerMethods<? extends BaseMethodsForEmployeeAndPost> getBaseController();
}
