package com.news_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private long newsId;
    @NotBlank(message = "Content can't be blank")
    @NotNull(message = "Content can't be null")
    @Size(min = 3, max = 255, message = "Content must have size from 3 to 255 letters")
    private String content;
}
