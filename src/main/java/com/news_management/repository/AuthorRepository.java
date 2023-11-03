package com.news_management.repository;

import com.news_management.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, PagingAndSortingRepository<Author, Long> {

    Author findByName(String authorName);

    List<Author> findByNameContaining(String partName);

    @Query("SELECT n.author FROM News n WHERE n.id = :newsId")
    Optional<Author> findByNewsId(@Param("newsId") Long newsId);

}
