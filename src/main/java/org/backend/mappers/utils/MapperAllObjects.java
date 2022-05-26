package org.backend.mappers.utils;

import org.backend.dto.BaseDto;
import org.backend.mappers.EmployeeMapper;
import org.backend.mappers.PostMapper;
import org.backend.models.Employee;
import org.backend.models.PostEmployee;
import org.mapstruct.factory.Mappers;

public class MapperAllObjects {

    private static MapperAllObjects instance;

    public static MapperAllObjects getInstance()
    {
        if(instance == null)
        {
            instance = new MapperAllObjects();
        }
        return  instance;
    }

    public BaseDto getDto(Employee employee)
    {
        return (BaseDto) Mappers.getMapper(EmployeeMapper.class).EmployeeToEmployeeDTO(employee);
    }

    public BaseDto getDto(PostEmployee postEmployee)
    {
        return (BaseDto) Mappers.getMapper(PostMapper.class).PostToPostDto(postEmployee);
    }
}
