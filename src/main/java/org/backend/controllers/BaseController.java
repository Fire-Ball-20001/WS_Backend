package org.backend.controllers;

import org.backend.data.DataSaverAndLoader;
import org.backend.models.BaseMethodsForEmployeeAndPost;
import lombok.NonNull;
import org.backend.utils.FindArgument;

import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class BaseController<T extends BaseMethodsForEmployeeAndPost> implements BaseControllerMethods<T> {
    private HashMap<UUID,T> arrays;
    @NonNull
    protected final DataSaverAndLoader dataSaverAndLoader;
    protected final Path file;

    public BaseController(@NonNull DataSaverAndLoader dataSaverAndLoader, Path file) {
        this.dataSaverAndLoader = dataSaverAndLoader;
        this.file = file;
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
        StringBuilder regex = new StringBuilder("(?m)");
        for(char ch :name.toCharArray())
        {
            regex.append(".*").append(ch);
        }
        Pattern pattern = Pattern.compile(regex.toString());
        List<UUID> uuids = new ArrayList<>();
        arrays.forEach(
                (UUID id, T object) ->
                {
                    if(pattern.matcher(object.getName()).find())
                    {
                        uuids.add(id);
                    }
                }
        );
        return uuids.toArray(new UUID[] {});
    }

    @Override
    public UUID[] findIdsByName(FindArgument argument) {
        return findIdsByName(argument.getName());
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

    public boolean deleteObjectById(UUID id)
    {
        if(!arrays.containsKey(id))
        {
            return false;
        }
        arrays.remove(id);
        return true;
    }

    public boolean saveData(Path file)
    {
        try {
            dataSaverAndLoader.createOrReplaceSaveData(arrays.values().toArray(new BaseMethodsForEmployeeAndPost[0]),file);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
    public boolean saveData() {
        return saveData(file);
    }

    public List<T> getAllObjects()
    {
        return new ArrayList<>(arrays.values());
    }

    public void setAllObjects(Map<UUID,T> new_objects)
    {
        arrays = new HashMap<>(new_objects);
    }
    public abstract String getDefaultFileName();
    public BaseController<T> getBaseController()
    {
        return this;
    }


    @Override
    public int getCountObjects() {
        return arrays.size();
    }

    @Override
    public List<T> getSortObjects() {
        return getAllObjects().stream().sorted().collect(Collectors.toList());
    }

}
