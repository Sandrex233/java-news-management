package com.news_management.controller;

import com.news_management.dto.CommentDTO;
import com.news_management.exceptions.EntityNotFoundException;
import com.news_management.model.Comment;
import com.news_management.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentsController {

    private final CommentService commentService;

    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping("{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) throws EntityNotFoundException {
        Comment comment = commentService.getCommentById(commentId);

        return ResponseEntity.ok(comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@RequestBody @Valid CommentDTO commentDTO, @PathVariable Long commentId) throws EntityNotFoundException {
        Comment comment = commentService.updateComment(commentDTO, commentId);

        return ResponseEntity.ok(comment);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody @Valid CommentDTO commentDTO) throws EntityNotFoundException {

        Comment createdComment = commentService.createComment(commentDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<?> deleteCommentById(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.deleteNewsById(commentId));
    }
}
