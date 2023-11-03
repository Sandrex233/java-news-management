package com.news_management.controller;

import com.news_management.dto.NewsDTO;
import com.news_management.model.News;
import com.news_management.services.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<List<News>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @GetMapping("/id/{Id}")
    public ResponseEntity<News> getNewsByID(@PathVariable Long Id) {
        return ResponseEntity.ok(newsService.getNewsById(Id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<News>> searchNews(
            @RequestParam(name = "tags", required = false) List<String> tags,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "title", required = false) String partOfTitle,
            @RequestParam(name = "content", required = false) String partOfContent) {
        return ResponseEntity.ok(newsService.searchNews(partOfTitle, tags, author, partOfContent));
    }

    @GetMapping("/Sorting")
    public ResponseEntity<List<News>> getNewsWithSorting(
            @RequestParam(defaultValue = "Created,ASC") String sortBy) {

        List<News> sortedNews = newsService.getNewsWithSorting(sortBy);

        return ResponseEntity.ok(sortedNews);
    }

    @GetMapping("/Pagination")
    public ResponseEntity<Page<News>> getNewsWithPagination(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        return ResponseEntity.ok(newsService.getNewsByPagination(pageNo, pageSize));
    }

    @GetMapping("/PaginationAndSorting")
    public ResponseEntity<Page<News>> getNewsWithPaginationAndSorting(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "Created,DESC") String sortBy) {

        return ResponseEntity.ok(newsService.getNewsWithPaginationAndSorting(pageNo, pageSize, sortBy));
    }

    @PostMapping
    public ResponseEntity<News> createNews(@RequestBody NewsDTO newsDTO) {
        News createdNews = newsService.createNews(newsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNews);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<News> updateNewsById(@RequestBody NewsDTO newsDTO, @PathVariable Long Id) {
        News updatedNews = newsService.updateNews(newsDTO, Id);
        return ResponseEntity.ok(updatedNews);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> deleteNewsById(@PathVariable Long Id) {
        return ResponseEntity.ok(newsService.deleteNewsById(Id));
    }
}
