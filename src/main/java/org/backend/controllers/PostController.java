package org.backend.controllers;

import org.backend.data.DataLoader;
import org.backend.data.DataSaver;
import org.backend.employee.PostEmployee;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PostController extends BaseController<PostEmployee> {


    public PostController(@NonNull DataSaver dataSaver, @NonNull DataLoader dataLoader, Path file) {
        super(dataSaver,dataLoader,file);
    }

    @Override
    void Init()
    {
        List<PostEmployee> posts = List.of(dataLoader.loadPostsData(file));
        setAllObjects(posts.stream().collect(Collectors.toMap(PostEmployee::getId, Function.identity())));
    }




}
