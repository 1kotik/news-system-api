package by.kotik.newsservice.dto;

import by.kotik.newsservice.annotation.UniqueCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@UniqueCategory
public class CategoryContentDto {
    @NotBlank(message = "Category cannot be blank")
    private String categoryName;
}
