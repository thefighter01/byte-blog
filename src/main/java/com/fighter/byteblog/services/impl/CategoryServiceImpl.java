package com.fighter.byteblog.services.impl;

import com.fighter.byteblog.domain.entities.Category;
import com.fighter.byteblog.repositories.CategoryRepository;
import com.fighter.byteblog.services.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount();
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("Category with name " + category.getName() + " already exists");
        }
        return categoryRepository.save(category);

    }

    @Override
    public void deleteCategory(UUID categoryId) { // we can delete entities which have not posts asscociated with them
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()){
            if (!category.get().getPosts().isEmpty()) {
                throw new IllegalStateException("Category cannot be deleted because it has posts associated with it");
            }
            else {
                categoryRepository.deleteById(categoryId);
            }
        }
    }
}
