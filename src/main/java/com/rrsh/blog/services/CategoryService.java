package com.rrsh.blog.services;

import com.rrsh.blog.model.CategoryDto;

import java.util.List;

public interface CategoryService {
    //create
    CategoryDto createCategory(CategoryDto categoryDto);
    //update
    CategoryDto updateCategory(CategoryDto categoryDto , int categoryId);
    //delete
     void deleteCategory(int categoryId);
     //get
     CategoryDto getCategoryById(int id);
     //getAll
     List<CategoryDto> getCategories();
}
