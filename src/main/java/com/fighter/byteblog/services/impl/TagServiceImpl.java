package com.fighter.byteblog.services.impl;

import com.fighter.byteblog.domain.entities.Tag;
import com.fighter.byteblog.repositories.TagRepository;
import com.fighter.byteblog.services.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl  implements TagService {

    private final TagRepository tagRepository;


    public List<Tag> listTags() {
        return tagRepository.findAllWithPostCount();
    }

    @Override
    @Transactional
    public List<Tag> createTags(Set<String> tags) {

        List<Tag> existingTags = tagRepository.findByNameIn(tags);
        Set<String> existingTagNames = existingTags.stream().map(Tag::getName).collect(Collectors.toSet());

        List<Tag> newTags = tags.stream()
                .filter(tag -> !existingTagNames.contains(tag))
                .map(tag -> Tag.builder().name(tag).posts(new HashSet<>()).build())
                .toList();


        List <Tag> savedTags = new ArrayList<>();
        if (!newTags.isEmpty()) {
            savedTags = tagRepository.saveAll(newTags);
        }
        savedTags.addAll(existingTags);
        return savedTags;
    }

    @Override
    @Transactional
    public void deleteTag(UUID tagId) {
        tagRepository.findById(tagId).ifPresent(tag -> {
           if ( !tag.getPosts().isEmpty()) {
               throw new IllegalStateException("Tag cannot be deleted because it has posts associated with it");
           }
           tagRepository.deleteById(tagId);
        });

    }
}
