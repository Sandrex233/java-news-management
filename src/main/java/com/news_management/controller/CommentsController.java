package com.news_management.controller;

import com.news_management.dto.CommentDTO;
import com.news_management.model.Comment;
import com.news_management.model.News;
import com.news_management.services.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentsController {

    private final CommentService commentService;

    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping("{commentId}")
    public ResponseEntity<Optional<Comment>> getCommentById(@PathVariable Long commentId) {
        Optional<Comment> comment = commentService.getCommentById(commentId);

        return ResponseEntity.ok(comment);
    }

    @GetMapping("/news/{newsId}")
    public ResponseEntity<Page<Comment>> getCommentsByNewsId (
            @PathVariable Long newsId,
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(defaultValue = "Created,DESC", required = false) String sortBy) {

        Page<Comment> comment = commentService.getCommentsByNewsIdWithPaginationAndSorting(newsId, pageNo, pageSize, sortBy);

        return ResponseEntity.ok(comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@RequestBody CommentDTO commentDTO, @PathVariable Long commentId) {
        Comment comment = commentService.updateComment(commentDTO, commentId);

        return ResponseEntity.ok(comment);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody CommentDTO commentDTO) {

        Comment createdComment = commentService.createComment(commentDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<?> deleteCommentById(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.deleteNewsById(commentId));
    }
}
