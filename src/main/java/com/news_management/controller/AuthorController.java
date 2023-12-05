package com.news_management.controller;

import com.news_management.dto.AuthorDTO;
import com.news_management.dto.AuthorNewsCountDTO;
import com.news_management.exceptions.EntityNotFoundException;
import com.news_management.model.Author;
import com.news_management.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<Page<Author>> getAllAuthors(
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        Page<Author> authors = authorService.getAllAuthors(pageNo, pageSize);
        return ResponseEntity.ok(authors);
    }


    @GetMapping("/{authorId}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long authorId) throws EntityNotFoundException {
        Author author = authorService.getAuthorById(authorId);
        return ResponseEntity.ok(author);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Author>> getAuthorByPartName(@RequestParam String partName) {
        List<Author> author = authorService.getAuthorByPartName(partName);
        return ResponseEntity.ok(author);
    }

    @GetMapping("/byAmountOfNews")
    public ResponseEntity<List<AuthorNewsCountDTO>> getAuthorsByAmountOfNews() {
        List<AuthorNewsCountDTO> authors = authorService.getAuthorsByAmountOfNews();
        return ResponseEntity.ok(authors);
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        Author author = authorService.createAuthor(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long authorId, @RequestBody @Valid AuthorDTO authorDTO) throws EntityNotFoundException {
        Author author = authorService.updateAuthor(authorId, authorDTO);
        return ResponseEntity.ok(author);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable Long authorId) throws EntityNotFoundException {
        return ResponseEntity.ok(authorService.deleteAuthorById(authorId));
    }
}
