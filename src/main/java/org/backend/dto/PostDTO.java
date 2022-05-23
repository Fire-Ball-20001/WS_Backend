package org.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class PostDTO implements BaseDto {
    private UUID id;
    private String name;
}
