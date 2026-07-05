package com.adnan.category.service.service.impl;

import com.adnan.category.service.dto.SalonDTO;
import com.adnan.category.service.modal.Category;
import com.adnan.category.service.repository.CategoryRepository;
import com.adnan.category.service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category, SalonDTO salonDTO) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setImage(category.getImage());
        newCategory.setSalonId(salonDTO.getId());
        return categoryRepository.save(newCategory);
    }

    @Override
    public Set<Category> getAllCategoriesBySalon(Long id) {
        return categoryRepository.findBySalonId(id);
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new Exception("Category not found with id: " + id);
        }
        return category;
    }

    @Override
    public Set<Category> getAllCategories() {
        return Set.copyOf(categoryRepository.findAll());
    }

    @Override
    public void deleteCategoryById(Long id, Long salonId) throws Exception {
        Category category = getCategoryById(id);
        if (!category.getSalonId().equals(salonId)) {
            throw new IllegalArgumentException("Category does not belong to the specified salon");
        }
        categoryRepository.delete(category);
    }
}
