package com.news_management.controller;

import com.news_management.dto.TagDTO;
import com.news_management.exceptions.EntityNotFoundException;
import com.news_management.model.Tag;
import com.news_management.services.TagService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<Page<Tag>> getAllTags(
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize
    ) {
        Page<Tag> tags = tagService.getAllTags(pageNo, pageSize);

        return ResponseEntity.ok(tags);
    }


    @GetMapping("/{tagId}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long tagId) throws EntityNotFoundException {
        Tag tag = tagService.getTagById(tagId);
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Tag>> getTagByPartName(@RequestParam String partName) {
        List<Tag> tag = tagService.getTagByPartName(partName);
        return ResponseEntity.ok(tag);
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long tagId, @RequestBody @Valid TagDTO tagDto) throws EntityNotFoundException {
        Tag tag = tagService.updateTag(tagId, tagDto);
        return ResponseEntity.ok(tag);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<?> deleteTagById(@PathVariable Long tagId) {
        return ResponseEntity.ok(tagService.deleteTagById(tagId));
    }

}
