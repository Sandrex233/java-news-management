package com.news_management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tblTag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Name", unique = true)
    @Size(min = 3, max = 15, message = "Title must be between 3 and 15 characters")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<News> news;

    public Tag(String name) {
        this.name = name;
    }
}
