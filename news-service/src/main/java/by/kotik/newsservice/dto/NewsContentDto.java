package by.kotik.newsservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsContentDto {
    @NotBlank(message = "News title cannot be blank")
    private String title;
    @NotBlank(message = "News content cannot be blank")
    private String content;
}
