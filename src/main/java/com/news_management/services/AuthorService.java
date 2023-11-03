package com.news_management.services;

import com.news_management.model.Author;
import com.news_management.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long authorId) {
        return authorRepository.findById(authorId);
    }

    public List<Author> getAuthorByPartName(String partName) {
        return authorRepository.findByNameContaining(partName);
    }

    public Optional<Author> getAuthorByNewsId(Long newsId) {
        return authorRepository.findByNewsId(newsId);
    }
}
