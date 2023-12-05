package com.news_management.services;

import com.news_management.dto.NewsDTO;
import com.news_management.exceptions.EntityNotFoundException;
import com.news_management.model.Author;
import com.news_management.model.News;
import com.news_management.model.Tag;
import com.news_management.repository.AuthorRepository;
import com.news_management.repository.CommentRepository;
import com.news_management.repository.NewsRepository;
import com.news_management.repository.TagRepository;
import com.news_management.utils.SortUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    private final AuthorRepository authorRepository;

    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;

    public NewsService(NewsRepository newsRepository, AuthorRepository authorRepository, TagRepository tagRepository, CommentRepository commentRepository) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public News createNews(NewsDTO newsDTO) {
        String authorName = newsDTO.getAuthor().getName();
        Author author = authorRepository.findByName(authorName);
        Set<Tag> tags = new HashSet<>();

        if (author == null) {
            author = new Author(authorName);
            authorRepository.save(author);
        }

        for (Tag tag : newsDTO.getTags()) {
            Tag existingTag = tagRepository.findByName(tag.getName());

            if (existingTag == null) {
                // Tag doesn't exist, use the provided tag
                tags.add(tag);
            } else {
                // Tag already exists, use the existing one
                tags.add(existingTag);
            }
        }

        News news = new News();
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setAuthor(author);
        news.setCreated(new Date());
        news.setTags(tags);

        return newsRepository.save(news);
    }

    @Transactional
    public News updateNews(NewsDTO newsDTO, Long Id) throws EntityNotFoundException {
        // Retrieve the existing news
        News news = newsRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException("News not found"));
        Author existingAuthor = authorRepository.findByName(newsDTO.getAuthor().getName());
        if (existingAuthor != null) {
            // Use the existing author
            news.setAuthor(existingAuthor);
        } else {
            // Create a new author
            Author newAuthor = new Author(newsDTO.getAuthor().getName());
            news.setAuthor(newAuthor);
        }

        // Check and update fields if they are provided in the request
        if (newsDTO.getTitle() != null) {
            news.setTitle(newsDTO.getTitle());
        }
        if (newsDTO.getContent() != null) {
            news.setContent(newsDTO.getContent());
        }

        // Handle tags
        if (newsDTO.getTags() != null) {
            Set<Tag> tags = new HashSet<>();
            for (Tag tag : newsDTO.getTags()) {
                Tag existingTag = tagRepository.findByName(tag.getName());
                if (existingTag == null) {
                    // Tag doesn't exist, use the provided tag
                    tags.add(tag);
                } else {
                    // Tag already exists, use the existing one
                    tags.add(existingTag);
                }
            }
            news.setTags(tags);
        }

        // Update the modified date
        news.setModified(new Date());

        return newsRepository.save(news);
    }

    public News getNewsById(Long Id) throws EntityNotFoundException {
        Optional<News> news = newsRepository.findById(Id);

        return news.orElseThrow(() -> new EntityNotFoundException("News with ID " + Id + " not found."));
    }

    public List<News> searchNews(String partOfTitle, List<String> tags, String author, String partOfContent) {
        Specification<News> spec = Specification.where(null);

        if (partOfTitle != null) {
            spec = spec.and((root, query, builder) ->
                    builder.like(root.get("title"), "%" + partOfTitle + "%"));
        }

        if (tags != null && !tags.isEmpty()) {
            spec = spec.and((root, query, builder) ->
                    root.join("tags").get("name").in(tags));
        }

        if (author != null) {
            spec = spec.and((root, query, builder) ->
                    builder.equal(root.get("author").get("name"), author));
        }

        if (partOfContent != null) {
            spec = spec.and((root, query, builder) ->
                    builder.like(root.get("content"), "%" + partOfContent + "%"));
        }

        return newsRepository.findAll(spec);
    }


    @Transactional
    public ResponseEntity<?> deleteNewsById(Long newsId) {
        News news = newsRepository.findById(newsId).orElse(null);

        if (news == null) {
            return ResponseEntity.notFound().build();
        }

        news.setAuthor(null);
        news.getTags().clear();

        newsRepository.delete(news);

        return ResponseEntity.ok().body("News with ID of " + newsId + " deleted successfully.");
    }

    public Page<News> getNewsWithPaginationAndSorting(Integer pageNo, Integer pageSize, String sortBy) {
        Sort sort = SortUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

        Page<News> page = newsRepository.findAll(pageRequest);

        return page;
    }


}
