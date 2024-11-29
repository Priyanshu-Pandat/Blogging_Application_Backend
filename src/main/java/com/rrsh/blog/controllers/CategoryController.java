package com.rrsh.blog.controllers;

import com.rrsh.blog.model.Apiresponce;
import com.rrsh.blog.model.CategoryDto;
import com.rrsh.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //Create
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid  @RequestBody CategoryDto catoDto)
    {
        CategoryDto categoryDto = this.categoryService.createCategory(catoDto);
        return  new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.CREATED);
    }
    //Update
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto catoDto , @PathVariable("id") int categoryId)
    {
       CategoryDto categoryDto = this.categoryService.updateCategory(catoDto, categoryId);
       return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }
    //Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Apiresponce> deleteCategory(@PathVariable int id) {
        this.categoryService.deleteCategory(id);
       return new ResponseEntity<>(new Apiresponce("category deleted successfully" , true), HttpStatus.OK);
    }
    //Get All
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categoryDtos = this.categoryService.getCategories();
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }
    //Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable int id) {
       CategoryDto categoryDto=  this.categoryService.getCategoryById(id);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }
}
