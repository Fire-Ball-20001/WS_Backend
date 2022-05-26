package org.backend.controllers;

import org.backend.models.BaseMethodsForEmployeeAndPost;
import org.backend.utils.FindArgument;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BaseControllerMethods<T extends BaseMethodsForEmployeeAndPost> {
    T getObjectById(UUID id);
    UUID getIdByObject(T object);
    UUID[] findIdsByName(String name);
    UUID[] findByFindArg(FindArgument argument);
    boolean addObject(T object);
    boolean setObject(T object);
    boolean deleteObjectById(UUID id);
    boolean saveData();
    List<T> getAllObjects();
    List<T> getSortObjects();
    void setAllObjects(Map<UUID,T> new_objects);
    String getDefaultFileName();
    int getCountObjects();
    UUID[] getRoughUUIDs(String uuid);
}
