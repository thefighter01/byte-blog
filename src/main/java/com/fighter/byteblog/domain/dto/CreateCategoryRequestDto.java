package com.fighter.byteblog.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCategoryRequestDto {

    @NotBlank(message = "Category name is required")
    @Size(min = 2 , max = 50 , message = "Category name must be between {min} and {max} characters")
    @Pattern(regexp = "^[\\w\\s-]+$" , message = "Category name must contain only letters , spaces and hyphens")
    private String name;
}
