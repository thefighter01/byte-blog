package com.fighter.byteblog.services;

import com.fighter.byteblog.domain.entities.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {

    List<Tag> listTags();

    List<Tag> createTags(Set<String> tags);

    void deleteTag(UUID tagId);
}
