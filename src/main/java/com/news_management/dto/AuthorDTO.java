package com.news_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    @NotBlank(message = "Author's name can't be blank")
    @NotNull(message = "Author's name can't be null")
    @Size(min = 3, max = 15, message = "Author must have a name with length from 3 to 15 letters")
    private String authorName;
}
