package com.news_management.services;

import com.news_management.dto.CommentDTO;
import com.news_management.exceptions.NewsNotFoundException;
import com.news_management.model.Comment;
import com.news_management.model.News;
import com.news_management.repository.CommentRepository;
import com.news_management.utils.SortUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private NewsService newsService;

    @Autowired
    private CommentRepository commentRepository;


    public Optional<Comment> getCommentById(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        return comment;
    }

    public Comment createComment(CommentDTO commentDTO) {
        News news = newsService.getNewsById(commentDTO.getNewsId());

        if (news == null) {
            throw new NewsNotFoundException("News not found for ID: " + commentDTO.getNewsId());
        }

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setNews(news);
        comment.setCreated(new Date());

        return commentRepository.save(comment);
    }

    public ResponseEntity<?> deleteNewsById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (comment == null) {
            return ResponseEntity.notFound().build();
        }

        commentRepository.delete(comment);

        return ResponseEntity.ok().body("Comment with ID of " + commentId + " deleted successfully.");
    }

    public Page<Comment> getCommentsByNewsIdWithPaginationAndSorting(Long newsId, Integer pageNo, Integer pageSize, String sortBy) {
        Sort sort = SortUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

        Page<Comment> page = commentRepository.findAllByNewsId(newsId, pageRequest);

        return page;
    }

    public Comment updateComment(CommentDTO commentDTO, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        comment.setContent(commentDTO.getContent());
        comment.setModified(new Date());

        return commentRepository.save(comment);
    }
}
