package com.adnan.category.service.controller;

import com.adnan.category.service.dto.SalonDTO;
import com.adnan.category.service.modal.Category;
import com.adnan.category.service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories/salon-owner")
public class SalonCategoryController {
    @Autowired
    private CategoryService  categoryService;

    @PostMapping()
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        SalonDTO  salonDTO = new SalonDTO();
        salonDTO.setId(1L);

        Category createdCategory = categoryService.saveCategory(category, salonDTO);
        return ResponseEntity.ok(createdCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) throws Exception{
        SalonDTO  salonDTO = new SalonDTO();
        salonDTO.setId(1L);

        categoryService.deleteCategoryById(id, salonDTO.getId());
        return ResponseEntity.ok("Category has been deleted successfully");
    }
}
