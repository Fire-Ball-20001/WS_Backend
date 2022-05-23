package org.backend.employee;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.backend.Main;
import org.backend.Mappers.EmployeeMapper;
import org.backend.controllers.BaseController;
import org.backend.controllers.BaseControllerMethods;
import org.backend.dto.BaseDto;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
public class Employee implements BaseMethodsForEmployeeAndPost,Comparable<Employee> {
    @NonNull
    @Builder.Default
    private final UUID id = UUID.randomUUID();
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @Builder.Default
    private String description = "";
    @NonNull
    private PostEmployee post;
    @Builder.Default
    private String image = "";
    @NonNull
    private String[] characteristics;

    @Override
    public String toString()
    {
        String result =
                "Имя: " + firstName + "\n" +
                "Фамилия: " + lastName + "\n" +
                        (description.isEmpty() ? "" : "Описание: "+description + "\n") +
                "Должность: "+ post.getName();
        StringBuilder charBuilder = new StringBuilder(result);
        if(characteristics.length > 0)
        {
            charBuilder.append("\nХарактеристика: ");
            for (String characteristic:
                    Arrays.stream(characteristics).sorted().collect(Collectors.toList())) {
                charBuilder.append("\n");
                charBuilder.append("\t").append(characteristic).append(";");
            }
        }
        return charBuilder.toString();
    }

    public String toSaveString() {
        String result = "id: " + id.toString() + "\n" +
                "firstName: " + firstName + "\n" +
                        "lastName: " + lastName + "\n" +
                        "description: "+(description.isEmpty() ? "none" : description) + "\n";
        if(post.equals(Main.postController.getDefaultPost()))
        {
            result+="postId: none\n";
        }
        else
        {
            result+="postId: "+ post.getId() + "\n";
        }

        StringBuilder charBuilder = new StringBuilder(result);
        charBuilder.append("characteristics: ");
        if(characteristics.length > 0)
        {
            charBuilder.append(String.join(";", characteristics));
        }
        else
        {
            charBuilder.append("none");
        }
        charBuilder.append("\n")
                .append("image: ")
                .append(image.isEmpty() ? "none" : image);
        return charBuilder.toString();
    }
    @Override
    public String getName()
    {
        return firstName+" "+lastName;
    }



    @Override
    public int compareTo(Employee employee) {
        switch (getFirstName().compareTo(employee.getFirstName()))
        {
            case 0:
            {
                return getLastName().compareTo(employee.getLastName());
            }
            case 1:
            {
                return 1;
            }
            case -1:
            {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public BaseControllerMethods<? extends BaseMethodsForEmployeeAndPost> getBaseController() {
        return Main.employeeController.getBaseController();
    }

    @Override
    public BaseDto getDto() {
        return (BaseDto) Mappers.getMapper(EmployeeMapper.class).EmployeeToEmployeeDTO(this);
    }
}
