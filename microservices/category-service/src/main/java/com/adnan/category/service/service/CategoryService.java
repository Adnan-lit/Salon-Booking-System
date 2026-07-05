package com.adnan.category.service.service;

import com.adnan.category.service.dto.SalonDTO;
import com.adnan.category.service.modal.Category;

import java.util.Set;

public interface CategoryService {

    Category saveCategory(Category category, SalonDTO salonDTO);

    Set<Category> getAllCategoriesBySalon(Long id);

    Category getCategoryById(Long id) throws Exception;

    Set<Category> getAllCategories();

    void deleteCategoryById(Long id, Long salonId) throws Exception;
}
