package com.news_management.controller;

import com.news_management.model.Author;
import com.news_management.services.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors(){
        List<Author> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }
    @GetMapping("/{authorId}")
    public ResponseEntity<Optional<Author>> getAuthorById(@PathVariable Long authorId){
        Optional<Author> author = authorService.getAuthorById(authorId);
        return ResponseEntity.ok(author);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Author>> getAuthorByPartName(@RequestParam String partName){
        List<Author> author = authorService.getAuthorByPartName(partName);
        return ResponseEntity.ok(author);
    }

    @GetMapping("/news/{newsId}")
    public ResponseEntity<Optional<Author>> getAuthorByNewsId(@PathVariable Long newsId){
        Optional<Author> author = authorService.getAuthorByNewsId(newsId);
        return ResponseEntity.ok(author);
    }
}
