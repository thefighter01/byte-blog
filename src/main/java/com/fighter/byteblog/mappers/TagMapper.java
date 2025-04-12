package com.fighter.byteblog.mappers;

import com.fighter.byteblog.domain.PostStatus;
import com.fighter.byteblog.domain.dto.TagResponseDto;
import com.fighter.byteblog.domain.entities.Post;
import com.fighter.byteblog.domain.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring" , unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    @Mapping(target = "postCount", source = "posts" , qualifiedByName = "calculatePostCount")
    TagResponseDto toTagResponseDto(Tag tag);


    @Named("calculatePostCount")
    default Integer calculatePostCount(Set<Post> posts) {
        if (null == posts) return 0;
        return (int)posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getPostStatus())).count();
    }



}
