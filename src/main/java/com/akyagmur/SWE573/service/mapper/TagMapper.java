package com.akyagmur.swe573.service.mapper;

import com.akyagmur.swe573.domain.Tag;
import com.akyagmur.swe573.service.dto.TagDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDTO}.t
 * ..,.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagDTO, Tag> {}
