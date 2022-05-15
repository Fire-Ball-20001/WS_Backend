package org.backend.files;

import org.backend.Main;
import org.backend.data.DataBuilder;
import org.backend.employee.Employee;
import org.backend.employee.PostEmployee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.backend.utils.Check;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class FileDataBuilder implements DataBuilder {
    @NonNull
    @Getter
    @Setter
    Check check;
    public Employee[] parseEmployees(String data) {
        String regex ="(?m)id:\\s(?<id>.+)" +
                "\\nfirstName:(?<firstname>\\s.+)" +
                "\\nlastName:\\s(?<lastname>.+)" +
                "\\ndescription:\\s(?<description>.+)" +
                "\\npostId:\\s(?<postid>.+)" +
                "\\ncharacteristics:\\s(?<charac>.+)" +
                "\\nimage:\\s(?<image>.+)";
        String[] split_data = data.split("\n\n");
        try{
            check.checkDataEmployees(split_data);
        }
        catch (RuntimeException e)
        {
            throw new RuntimeException("Invalid org.backend.data format");
        }
        List<Employee> employeeList = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        for (String employee_string:
             split_data) {
            Matcher matcher = pattern.matcher(employee_string);
            while(matcher.find())
            {
                String description = matcher.group("description");
                String image = matcher.group("image");
                Employee.EmployeeBuilder employee = Employee.builder()
                        .id(UUID.fromString(matcher.group("id")))
                        .firstName(matcher.group("firstname"))
                        .lastName(matcher.group("lastname"))
                        .characteristics(matcher.group("charac").split(";"))
                        .post(
                                Main.postController.getObjectById(
                                        UUID.fromString(
                                                matcher.group("postId"))));
                if(!description.equals("none"))
                {
                    employee.description(matcher.group("description"));
                }
                if(!image.equals("none"))
                {
                    employee.image(matcher.group("image"));
                }
                employeeList.add(employee.build());
            }

        }
        return employeeList.toArray(new Employee[] {});
    }

    public PostEmployee[] parsePostEmployees(String data) {
        return null;
    }

    public String getString(PostEmployee[] postEmployees)
    {
        return null;
    }
    public String getString(Employee[] postEmployees)
    {
        return null;
    }
}
