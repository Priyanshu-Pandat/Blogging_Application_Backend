package com.rrsh.blog.services;

import com.rrsh.blog.entities.Category;
import com.rrsh.blog.exceptions.ResourceNotFoundException;
import com.rrsh.blog.model.CategoryDto;
import com.rrsh.blog.reopsitories.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
       Category cat =  this.modelMapper.map(categoryDto, Category.class);
      Category addedCat= this.categoryRepo.save(cat);
        return this.modelMapper.map(addedCat, CategoryDto.class) ;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
        Category cat =  this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException
                ("Category", "id", categoryId));
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedcat = this.categoryRepo.save(cat);
        return this.modelMapper.map(updatedcat , CategoryDto.class);

    }

    @Override
    public void deleteCategory(int categoryId) {
      Category cat =  this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException
                ("Category", "id", categoryId));
       this.categoryRepo.delete(cat);

    }

    @Override
    public CategoryDto getCategoryById(int id) {
        Category cat =  this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException
                ("Category", "id", id));;

        return this.modelMapper.map(cat ,CategoryDto.class );
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> catDtos = categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        return catDtos;
    }
}
