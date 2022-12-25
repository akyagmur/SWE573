package com.akyagmur.swe573.service.mapper;

import com.akyagmur.swe573.domain.Post;
import com.akyagmur.swe573.service.dto.PostDTO;

import java.util.List;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 * .
 */
@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDTO, Post> {
    // post tag many to many relation
    @Mapping(target = "tags", source = "post.tags")
    PostDTO toDto(Post post);

    List<PostDTO> toDto(List<Post> posts);

    @Mapping(target = "tags", source = "postDTO.tags")
    Post toEntity(PostDTO postDTO);

    List<Post> toEntity(List<PostDTO> postDTOs);
}
