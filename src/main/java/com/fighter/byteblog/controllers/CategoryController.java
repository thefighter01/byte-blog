package com.fighter.byteblog.controllers;


import com.fighter.byteblog.domain.dto.CategoryDto;
import com.fighter.byteblog.domain.dto.CreateCategoryRequestDto;
import com.fighter.byteblog.domain.entities.Category;
import com.fighter.byteblog.mappers.CategoryMapper;
import com.fighter.byteblog.services.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path= "/api/v1/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;


    @GetMapping
    public ResponseEntity<List<CategoryDto> > listCategories() {
        List<CategoryDto> categoryDtoList = categoryService.listCategories().stream().map(categoryMapper::toDto).toList();
       return ResponseEntity.ok(categoryDtoList);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CreateCategoryRequestDto createCategoryDto) {
        Category categoryToCreate = categoryMapper.toEntity(createCategoryDto);
        Category createdCategory = categoryService.createCategory(categoryToCreate);
        CategoryDto categoryDto = categoryMapper.toDto(createdCategory);
        return new ResponseEntity<>(categoryDto ,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") UUID categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
