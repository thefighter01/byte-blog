package com.fighter.byteblog.controllers;


import com.fighter.byteblog.domain.dto.TagResponseDto;
import com.fighter.byteblog.domain.dto.TagsRequestDto;
import com.fighter.byteblog.domain.entities.Tag;
import com.fighter.byteblog.mappers.TagMapper;
import com.fighter.byteblog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/tags")
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;
    private final ResourceUrlProvider resourceUrlProvider;


    @GetMapping
    public ResponseEntity<List<TagResponseDto>> listTags(){
        List<TagResponseDto> tagResponseDtoList = tagService.listTags().stream().map(tagMapper::toTagResponseDto).toList();

        return ResponseEntity.ok(tagResponseDtoList);

    }

    @PostMapping
    public ResponseEntity<List<TagResponseDto>> createTag(@RequestBody TagsRequestDto tagsRequestDto){
        List<Tag> savedTags = tagService.createTags(tagsRequestDto.getNames());
        List<TagResponseDto> tagResponseDtoList = savedTags.stream().map(tagMapper::toTagResponseDto).toList();
        return new ResponseEntity<>(tagResponseDtoList , HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id ){
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
