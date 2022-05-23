package org.backend.utils;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindArgument {
    @Builder.Default
    private String name = "";
    @Builder.Default
    private String postName = "";
}
