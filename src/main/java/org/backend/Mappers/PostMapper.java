package org.backend.Mappers;

import org.backend.dto.PostDTO;
import org.backend.employee.PostEmployee;
import org.mapstruct.Mapper;

@Mapper
public interface PostMapper {
    PostDTO PostToPostDto(PostEmployee post);
    PostEmployee PostDtoToPost(PostDTO dto);
}
