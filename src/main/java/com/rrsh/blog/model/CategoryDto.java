package com.rrsh.blog.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private int categoryId;
    @NotEmpty(message = "category not be empty")
    private String categoryTitle;
    @NotEmpty(message = "category description not be empty")
    @Size(min = 8 , max = 100)
    private String categoryDescription;
}
