package org.backend.utils;


import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class FindArgument {
    @Builder.Default
    private String name = "";
    private UUID id;
}
