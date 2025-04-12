package com.fighter.byteblog.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagsRequestDto {

    @Size(min=10 ,  message = "Maximum {max} tags allowed")
    @NotEmpty(message = "At least one tag is required")
    private Set<@Size(min = 2 , max = 30 , message = "Tag name must be between {min} and {max} characters")
            @Pattern(regexp = "^[\\w\\s-]+$" , message = "Tag name must contain only letters , spaces and hyphens") String> names;
}
