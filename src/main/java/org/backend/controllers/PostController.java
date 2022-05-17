package org.backend.controllers;

import org.backend.Main;
import org.backend.config.Config;
import org.backend.data.DataLoader;
import org.backend.data.DataSaver;
import org.backend.employee.PostEmployee;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PostController extends BaseController<PostEmployee> {


    public PostController(@NonNull DataSaver dataSaver, @NonNull DataLoader dataLoader, Path file) {
        super(dataSaver,dataLoader,file);
    }

    @Override
    public void Init()
    {
        PostEmployee[] posts_array;
        try {
            posts_array = dataLoader.loadPostsData(file);
        }
        catch (RuntimeException e)
        {
            Main.output.errorLoadPost();
            posts_array = new PostEmployee[]
            {
                    new PostEmployee(UUID.randomUUID(),"Младший"),
                            new PostEmployee(UUID.randomUUID(),"Средний"),
                            new PostEmployee(UUID.randomUUID(),"Старший")
            };
        }
        List<PostEmployee> posts = List.of(posts_array);
        setAllObjects(posts.stream().collect(Collectors.toMap(PostEmployee::getId, Function.identity())));
    }

    @Override
    public String getDefaultFileName() {
        return Config.POST_FILE_NAME;
    }

    public PostEmployee getDefaultPost()
    {
        return new PostEmployee(UUID.fromString("00000000-0000-0000-0000-000000000000"),"Нет должности");
    }

    @Override
    public PostEmployee getObjectById(UUID id) {
        if(id.equals(getDefaultPost().getId()))
        {
            return getDefaultPost();
        }
        else {
            return super.getObjectById(id);
        }
    }

    @Override
    public boolean addObject(PostEmployee object) {
        Main.observer.update();
        return super.addObject(object);
    }

    @Override
    public boolean setObject(PostEmployee object) {
        Main.observer.update();
        return super.setObject(object);
    }

    @Override
    public boolean deleteObjectById(UUID id) {
        Main.observer.update();
        return super.deleteObjectById(id);
    }

}
