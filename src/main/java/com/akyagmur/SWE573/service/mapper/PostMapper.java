package com.akyagmur.swe573.service.mapper;

import com.akyagmur.swe573.domain.Post;
import com.akyagmur.swe573.service.dto.PostDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDTO, Post> {}
