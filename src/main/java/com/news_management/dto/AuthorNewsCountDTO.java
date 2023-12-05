package com.news_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorNewsCountDTO {
    private Long id;
    private String name;
    private Long amountOfWrittenNews;
}
