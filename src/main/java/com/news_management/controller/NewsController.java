package com.news_management.controller;

import com.news_management.dto.NewsDTO;
import com.news_management.exceptions.EntityNotFoundException;
import com.news_management.model.Author;
import com.news_management.model.Comment;
import com.news_management.model.News;
import com.news_management.model.Tag;
import com.news_management.services.AuthorService;
import com.news_management.services.CommentService;
import com.news_management.services.NewsService;
import com.news_management.services.TagService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService newsService;
    private final CommentService commentService;
    private final AuthorService authorService;

    private final TagService tagService;


    public NewsController(NewsService newsService, CommentService commentService, AuthorService authorService, TagService tagService) {
        this.newsService = newsService;
        this.commentService = commentService;
        this.authorService = authorService;
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<Page<News>> getNewsWithPaginationAndSorting(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "Created,DESC") String sortBy) {

        return ResponseEntity.ok(newsService.getNewsWithPaginationAndSorting(pageNo, pageSize, sortBy));
    }

    @GetMapping("/id/{Id}")
    public ResponseEntity<News> getNewsByID(@PathVariable Long Id) throws EntityNotFoundException {
        return ResponseEntity.ok(newsService.getNewsById(Id));
    }

    @GetMapping("/{newsId}/comments")
    public ResponseEntity<Page<Comment>> getCommentsByNewsId(
            @PathVariable Long newsId,
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(defaultValue = "Created,DESC", required = false) String sortBy) throws EntityNotFoundException {

        Page<Comment> comment = commentService.getCommentsByNewsIdWithPaginationAndSorting(newsId, pageNo, pageSize, sortBy);

        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{newsId}/authors")
    public ResponseEntity<Author> getAuthorByNewsId(@PathVariable Long newsId) throws EntityNotFoundException {
        Author author = authorService.getAuthorByNewsId(newsId);
        return ResponseEntity.ok(author);
    }

    @GetMapping("/{newsId}/tags")
    public ResponseEntity<List<Tag>> getTagsByNewsId(@PathVariable Long newsId) throws EntityNotFoundException {
        List<Tag> tags = tagService.getTagsByNewsId(newsId);
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/search")
    public ResponseEntity<List<News>> searchNews(
            @RequestParam(name = "tags", required = false) List<String> tags,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "title", required = false) String partOfTitle,
            @RequestParam(name = "content", required = false) String partOfContent) {
        return ResponseEntity.ok(newsService.searchNews(partOfTitle, tags, author, partOfContent));
    }

    @PostMapping
    public ResponseEntity<News> createNews(@RequestBody @Valid NewsDTO newsDTO) {
        News createdNews = newsService.createNews(newsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNews);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<News> updateNewsById(@RequestBody NewsDTO newsDTO, @PathVariable Long Id) throws EntityNotFoundException {
        News updatedNews = newsService.updateNews(newsDTO, Id);
        return ResponseEntity.ok(updatedNews);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> deleteNewsById(@PathVariable Long Id) {
        return ResponseEntity.ok(newsService.deleteNewsById(Id));
    }
}