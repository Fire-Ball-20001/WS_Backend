package org.backend.employee;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
public class Employee implements BaseMethodsForEmployeeAndPost {
    @NonNull
    private final UUID id;
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
                 characteristics) {
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
                        "description: "+(description.isEmpty() ? "none" : description) + "\n" +
                        "postId: "+ post.getId() + "\n";
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
}
