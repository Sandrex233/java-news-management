package com.news_management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tblAuthor")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Name", unique = true)
    @Size(min = 3, max = 15, message = "Name must be between 3 and 15 characters")
    private String name;

    public Author(String name) {
        this.name = name;
    }
}
