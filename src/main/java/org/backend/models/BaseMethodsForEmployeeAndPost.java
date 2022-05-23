package org.backend.models;

import org.backend.controllers.BaseControllerMethods;
import org.backend.dto.BaseDto;

import java.util.UUID;

public interface BaseMethodsForEmployeeAndPost {
    UUID getId();
    String getName();
    String toSaveString();
    BaseControllerMethods<? extends BaseMethodsForEmployeeAndPost> getBaseController();
    BaseDto getDto();
}
