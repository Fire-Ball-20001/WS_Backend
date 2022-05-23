package org.backend.Mappers;

import org.backend.Main;
import org.backend.dto.EmployeeDTO;
import org.backend.models.Employee;
import org.backend.models.PostEmployee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface EmployeeMapper {

    @Mapping(target = "post", source = "post_id")
    Employee EmployeeDtoToEmployee(EmployeeDTO dto);
    @Mapping(target = "post_id", source = "post.id")
    EmployeeDTO EmployeeToEmployeeDTO(Employee employee);
    default PostEmployee PostIdToPost(UUID post_id)
    {
        return Main.postController.getObjectById(post_id);
    }
}
