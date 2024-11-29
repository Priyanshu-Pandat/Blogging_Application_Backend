package com.rrsh.blog.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;
    @NotEmpty
    @Size(min = 4 , message = "UserName must be min of 4 characters")
    private String name;
    @Email(message = "email address is not valid !!")
    private String email;
    @NotEmpty(message = "password is necessary")
    @Size(min = 4 , max = 10 , message = "Password must be at least 4 to 10 size")
    //@Pattern(regexp = "^[a-zA-Z0-9]")
    private String password;
    @NotEmpty
    private String about;
}
