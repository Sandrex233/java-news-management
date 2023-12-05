package com.news_management.services;

import com.news_management.dto.AuthorDTO;
import com.news_management.dto.AuthorNewsCountDTO;
import com.news_management.exceptions.EntityNotFoundException;
import com.news_management.model.Author;
import com.news_management.model.Comment;
import com.news_management.repository.AuthorRepository;
import com.news_management.utils.SortUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Page<Author> getAllAuthors(Integer pageNo,Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

        Page<Author> authors = authorRepository.findAll(pageRequest);

        return authors;
    }

    public Author getAuthorById(Long authorId) throws EntityNotFoundException {
        Optional<Author> author = authorRepository.findById(authorId);

        return author.orElseThrow(() -> new EntityNotFoundException("Author with ID " + authorId + " not found"));
    }

    public List<Author> getAuthorByPartName(String partName) {
        return authorRepository.findByNameContaining(partName);
    }

    public Author getAuthorByNewsId(Long newsId) throws EntityNotFoundException {
        Author author = authorRepository.findByNewsId(newsId).orElseThrow(() -> new EntityNotFoundException("Author with News ID "+ newsId + " not found"));

        return author;
        }

    public Author createAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getAuthorName());
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long authorId, AuthorDTO authorDTO) throws EntityNotFoundException {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new EntityNotFoundException("Author with ID : " + authorId + " not found"));
        author.setName(authorDTO.getAuthorName());
        return authorRepository.save(author);
    }

    public ResponseEntity<?> deleteAuthorById(Long authorId) throws EntityNotFoundException {
        Author author = authorRepository.findById(authorId).orElse(null);

        if (author == null) {
            throw new EntityNotFoundException("Author with ID of " + authorId + " not found.");
        }

        authorRepository.delete(author);

        return ResponseEntity.ok().body("Author with ID of " + authorId + " deleted successfully.");
    }

    public List<AuthorNewsCountDTO> getAuthorsByAmountOfNews() {
        List<AuthorNewsCountDTO> authors = authorRepository.findAuthorsByNewsCount();
        return authors;
    }
}
