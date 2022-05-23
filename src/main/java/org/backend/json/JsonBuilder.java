package org.backend.json;

import com.google.gson.JsonArray;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.backend.Main;
import org.backend.Mappers.EmployeeMapper;
import org.backend.Mappers.PostMapper;
import org.backend.data.DataBuilder;
import org.backend.dto.EmployeeDTO;
import org.backend.dto.PostDTO;
import org.backend.employee.BaseMethodsForEmployeeAndPost;
import org.backend.employee.Employee;
import org.backend.employee.PostEmployee;
import org.backend.utils.Check;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;

@AllArgsConstructor
public class JsonBuilder implements DataBuilder {

    @Getter
            @Setter
            @NonNull
    Check check;

    @Override
    public Employee[] parseEmployees(String data) {
        EmployeeDTO[] employeeDTOS;
        try {
            employeeDTOS = Main.gson.fromJson(data,EmployeeDTO[].class);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Invalid data format");
        }
        return Arrays.stream(employeeDTOS).map(
                (EmployeeDTO dto) ->
                        Mappers.getMapper(EmployeeMapper.class).EmployeeDtoToEmployee(dto)
        ).toArray(Employee[]::new);
    }

    @Override
    public PostEmployee[] parsePostEmployees(String data) {
        PostDTO[] employeeDTOS;
        try {
            employeeDTOS = Main.gson.fromJson(data, PostDTO[].class);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Invalid posts data format");
        }
        return Arrays.stream(employeeDTOS).map(
                (PostDTO dto) ->
                        Mappers.getMapper(PostMapper.class).PostDtoToPost(dto)
        ).toArray(PostEmployee[]::new);
    }

    @Override
    public String getSaveString(BaseMethodsForEmployeeAndPost[] objects) {
        JsonArray array = new JsonArray();
        Arrays.stream(objects).forEach(
                (BaseMethodsForEmployeeAndPost object) ->
                        array.add(Main.gson.toJsonTree(object.getDto()))
        );
        return Main.gson.toJson(array);
    }
}
