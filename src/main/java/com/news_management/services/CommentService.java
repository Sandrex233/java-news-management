package com.news_management.services;

import com.news_management.dto.CommentDTO;
import com.news_management.exceptions.EntityNotFoundException;
import com.news_management.model.Comment;
import com.news_management.model.News;
import com.news_management.repository.CommentRepository;
import com.news_management.utils.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private NewsService newsService;

    @Autowired
    private CommentRepository commentRepository;


    public Comment getCommentById(Long commentId) throws EntityNotFoundException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment with Id " + commentId + " not found"));
        return comment;
    }

    public Comment createComment(CommentDTO commentDTO) throws EntityNotFoundException {
        News news = newsService.getNewsById(commentDTO.getNewsId());

        if (news == null) {
            throw new EntityNotFoundException("News not found for ID: " + commentDTO.getNewsId());
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

    public Page<Comment> getCommentsByNewsIdWithPaginationAndSorting(Long newsId, Integer pageNo, Integer pageSize, String sortBy) throws EntityNotFoundException {
        Sort sort = SortUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

        Page<Comment> page = commentRepository.findAllByNewsId(newsId, pageRequest);

        if(!page.getContent().isEmpty()) {
            return page;
        } else {
            throw new EntityNotFoundException("News with Id " + newsId + " not found");
        }
    }

    public Comment updateComment(CommentDTO commentDTO, Long commentId) throws EntityNotFoundException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        comment.setContent(commentDTO.getContent());
        comment.setModified(new Date());

        return commentRepository.save(comment);
    }
}
