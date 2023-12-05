package com.news_management.dto;

import com.news_management.model.Author;
import com.news_management.model.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class NewsDTO {
    @NotBlank(message = "Title can't be blank")
    @NotNull(message = "Title can't be null")
    @Size(min = 5, max = 30, message = "Title must have size from 5 to 30 letters")
    private String title;
    @NotBlank(message = "Content can't be blank")
    @NotNull(message = "Content can't be null")
    @Size(min = 5, max = 255, message = "Content must have size from 5 to 255 letters")
    private String content;
    private Author author;
    private Set<Tag> tags;
}
