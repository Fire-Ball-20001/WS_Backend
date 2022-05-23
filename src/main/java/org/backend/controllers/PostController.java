package org.backend.controllers;

import org.backend.Main;
import org.backend.config.Config;
import org.backend.data.DataSaverAndLoader;
import org.backend.models.PostEmployee;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PostController extends BaseController<PostEmployee> {


    public PostController(@NonNull DataSaverAndLoader dataSaverAndLoader, Path file) {
        super(dataSaverAndLoader,file);
    }

    @Override
    public void Init()
    {
        PostEmployee[] posts_array;
        try {
            posts_array = dataSaverAndLoader.loadPostsData(file);
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
        List<PostEmployee> posts = Arrays.asList(posts_array);
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
        try {
            return super.getObjectById(id);
        }
        catch (RuntimeException e)
        {
            return getDefaultPost();
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
