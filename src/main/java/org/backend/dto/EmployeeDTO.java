package org.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String description;
    private UUID post_id;
    private String image;
    private String[] characteristics;

}
