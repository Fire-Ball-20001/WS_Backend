package org.backend.mappers;

import org.backend.Main;
import org.backend.dto.EmployeeDTO;
import org.backend.models.Employee;
import org.backend.models.PostEmployee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface EmployeeMapper{

    @Mapping(target = "post", source = "post")
    @Mapping(target = "id",source = "dto.id")
    Employee EmployeeDtoToEmployee(EmployeeDTO dto,PostEmployee post);
    @Mapping(target = "post_id", source = "post.id")
    EmployeeDTO EmployeeToEmployeeDTO(Employee employee);
}
