package org.backend.controllers;

import org.backend.data.DataLoader;
import org.backend.data.DataSaver;
import org.backend.employee.BaseMethodsForEmployeeAndPost;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.*;

public abstract class BaseController<T extends BaseMethodsForEmployeeAndPost> {
    private HashMap<UUID,T> arrays;
    @NonNull
    protected final DataSaver dataSaver;
    @NonNull
    protected final DataLoader dataLoader;
    protected final Path file;

    public BaseController(@NonNull DataSaver dataSaver, @NonNull DataLoader dataLoader, Path file) {
        this.dataSaver = dataSaver;
        this.dataLoader = dataLoader;
        this.file = file;
        Init();
    }

    abstract void Init();


    public T getObjectById(UUID id)
    {
        if(!arrays.containsKey(id))
            throw new RuntimeException("No element found matching this id: "+id.toString());
        return arrays.get(id);
    }
    public UUID getIdByObject(T object)
    {
        if(!arrays.containsValue(object))
            throw new RuntimeException("No element found matching this org.backend.employee");
        return object.getId();
    }
    public UUID[] findIdsByName(String name)
    {
        List<UUID> uuids = new ArrayList<>();
        arrays.forEach(
                (UUID id, T object) ->
                {
                    if(object.getName().equals(name))
                    {
                        uuids.add(id);
                    }
                }
        );
        return uuids.toArray(new UUID[] {});
    }

    public boolean addObject(T object)
    {
        if(arrays.containsKey(object.getId()))
        {
            return false;
        }
        arrays.put(object.getId(),object);
        return true;
    }

    public boolean setObject(T object)
    {
        if(!arrays.containsKey(object.getId()))
        {
            return false;
        }
        arrays.replace(object.getId(),object);
        return true;
    }

    public boolean deleteEmployee(UUID id)
    {
        if(!arrays.containsKey(id))
        {
            return false;
        }
        arrays.remove(id);
        return true;
    }

    public boolean saveData()
    {
        try {
            dataSaver.createOrReplaceSaveData(arrays.values().toArray(new BaseMethodsForEmployeeAndPost[0]),file);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
    public T[] getAllObjects()
    {
        return (T[]) arrays.values().toArray(new BaseMethodsForEmployeeAndPost[0]);
    }

    protected void setAllObjects(Map<UUID,T> new_objects)
    {
        arrays = new HashMap<>(new_objects);
    }

}
