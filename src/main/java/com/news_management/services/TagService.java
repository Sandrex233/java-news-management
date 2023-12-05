package com.news_management.services;

import com.news_management.dto.TagDTO;
import com.news_management.exceptions.EntityNotFoundException;
import com.news_management.model.Tag;
import com.news_management.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;


    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    public Page<Tag> getAllTags(Integer pageNo, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

        return tagRepository.findAll(pageRequest);
    }

    public Tag getTagById(Long tagId) throws EntityNotFoundException {
        return tagRepository.findById(tagId).orElseThrow(() -> new EntityNotFoundException("Tag with an Id of " + tagId + " not found"));
    }

    public List<Tag> getTagByPartName(String partName) {
        return tagRepository.findByNameContaining(partName);
    }

    public List<Tag> getTagsByNewsId(Long newsId) throws EntityNotFoundException {
        List<Tag> tags = tagRepository.findByNewsId(newsId);

        if (!tags.isEmpty()) {
            return tags;
        } else {
            throw new EntityNotFoundException("Tags with News Id " + newsId + " not found");
        }
    }


    public Tag updateTag(Long tagID, TagDTO tagDTO) throws EntityNotFoundException {
        Tag tag = tagRepository.findById(tagID).orElseThrow(() -> new EntityNotFoundException("Tag not found"));
        tag.setName(tagDTO.getTagName());
        return tagRepository.save(tag);
    }

    public ResponseEntity<?> deleteTagById(Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElse(null);

        if (tag == null) {
            return ResponseEntity.notFound().build();
        }

        tagRepository.delete(tag);

        return ResponseEntity.ok().body("Tag with ID of " + tagId + " deleted successfully.");
    }

}
