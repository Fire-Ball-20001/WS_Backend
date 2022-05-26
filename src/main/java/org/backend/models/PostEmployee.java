package org.backend.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.backend.Main;
import org.backend.mappers.PostMapper;
import org.backend.controllers.BaseControllerMethods;
import org.backend.dto.BaseDto;
import org.backend.mappers.utils.MapperAllObjects;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PostEmployee implements BaseMethodsForEmployeeAndPost,Comparable<PostEmployee> {
    @NonNull
    private final UUID id;
    @NonNull
    private final String name;

    @Override
    public String toSaveString() {
        return id+": "+name;
    }

    @Override
    public BaseControllerMethods<? extends BaseMethodsForEmployeeAndPost> getBaseController() {
        return Main.postController.getBaseController();
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public int compareTo(PostEmployee postEmployee) {
        return getName().compareTo(postEmployee.getName());
    }

    @Override
    public BaseDto getDto() {
        return MapperAllObjects.getInstance().getDto(this);
    }
}
