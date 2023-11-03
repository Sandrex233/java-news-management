package com.news_management.dto;

import com.news_management.model.Author;
import com.news_management.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class NewsDTO {
    private String title;
    private String content;
    private Author author;
    private Set<Tag> tags;
}
