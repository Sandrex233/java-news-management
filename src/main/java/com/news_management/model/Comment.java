package com.news_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tblComment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Content")
    @Size(min=3, max = 255, message = "Content must be between 3 and 255 characters")
    private String content;

    @ManyToOne
    @JoinColumn(name = "NewsId")
    @JsonIgnore
    private News news;

    @Column(name = "Created")
    private Date created;

    @Column(name = "Modified")
    private Date modified;

    public Comment(String content, News news, Date created, Date modified) {
        this.content = content;
        this.news = news;
        this.created = created;
        this.modified = modified;
    }
}
