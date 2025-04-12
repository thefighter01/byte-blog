package com.fighter.byteblog.mappers;


import com.fighter.byteblog.domain.PostStatus;
import com.fighter.byteblog.domain.dto.CategoryDto;
import com.fighter.byteblog.domain.dto.CreateCategoryRequestDto;
import com.fighter.byteblog.domain.entities.Category;
import com.fighter.byteblog.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring" , unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "postCount", source = "posts" , qualifiedByName = "calculatePostCount")
    CategoryDto toDto(Category category);

    Category toEntity(CreateCategoryRequestDto createCategoryRequestDto);

    @Named("calculatePostCount")
    default long calculatePostCount(List<Post> posts) {
        if (null == posts) return 0;
        return posts.stream().filter(post -> post.getPostStatus() == PostStatus.PUBLISHED).count();
    }
}
// map struct is going to implement this interface for us