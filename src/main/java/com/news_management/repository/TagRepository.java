package com.news_management.repository;

import com.news_management.model.Author;
import com.news_management.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String tagName);
    List<Tag> findByNameContaining(String partName);
    @Query("SELECT n.tags FROM News n WHERE n.id = :newsId")
    List<Tag> findByNewsId(@Param("newsId") Long newsId);
}
