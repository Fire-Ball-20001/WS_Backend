package org.backend.files;

import org.backend.Main;
import org.backend.config.Config;
import org.backend.data.DataBuilder;
import org.backend.employee.BaseMethodsForEmployeeAndPost;
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
        String regex = Config.REGEX_EMPLOYEE;
        String[] split_data = data.split("\\n\\n");
        try {
            check.checkDataEmployees(split_data);
        } catch (RuntimeException e) {
            throw new RuntimeException("Invalid data format");
        }
        List<Employee> employeeList = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        for (String employee_string :
                split_data) {
            Matcher matcher = pattern.matcher(employee_string);
            while (matcher.find()) {
                String description = matcher.group("description");
                String image = matcher.group("image");
                String postId = matcher.group("postid");
                Employee.EmployeeBuilder employee = Employee.builder()
                        .id(UUID.fromString(matcher.group("id")))
                        .firstName(matcher.group("firstname"))
                        .lastName(matcher.group("lastname"))
                        .characteristics(matcher.group("charac").split(";"));
                if (postId.equals("none")) {
                    employee.post(Main.postController.getDefaultPost());
                } else {
                    try {
                        employee.post(
                                Main.postController.getObjectById(
                                        UUID.fromString(postId)));
                    }
                    catch (RuntimeException e)
                    {
                        employee.post(Main.postController.getDefaultPost());
                    }
                }
                if (!description.equals("none")) {
                    employee.description(matcher.group("description"));
                }
                if (!image.equals("none")) {
                    employee.image(matcher.group("image"));
                }
                employeeList.add(employee.build());
            }

        }
        return employeeList.toArray(new Employee[]{});
    }

    public PostEmployee[] parsePostEmployees(String data) {

        Pattern pattern = Pattern.compile(Config.REGEX_POST);
        String[] split_data = data.split("\\n\\n");
        try {
            check.checkDataPosts(split_data);
        } catch (RuntimeException e) {
            throw new RuntimeException("Invalid posts data format");
        }
        List<PostEmployee> postEmployees = new ArrayList<>();
        for (String one_post :
                split_data) {
            Matcher matcher = pattern.matcher(one_post);
            while (matcher.find()) {
                postEmployees.add(new PostEmployee(
                        UUID.fromString(matcher.group("postId")),
                        matcher.group("postName")));
            }

        }
        return postEmployees.toArray(new PostEmployee[0]);
    }

    public String getSaveString(BaseMethodsForEmployeeAndPost[] baseObjects) {
        List<String> resultStrings = new ArrayList<>();
        for (BaseMethodsForEmployeeAndPost baseObject :
                baseObjects
        ) {
            resultStrings.add(baseObject.toSaveString());
        }

        return String.join("\n\n", resultStrings);
    }
}
