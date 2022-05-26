package org.backend.mappers;

import org.backend.dto.PostDTO;
import org.backend.models.PostEmployee;
import org.mapstruct.Mapper;

@Mapper
public interface PostMapper {
    PostDTO PostToPostDto(PostEmployee post);
    PostEmployee PostDtoToPost(PostDTO dto);
}
