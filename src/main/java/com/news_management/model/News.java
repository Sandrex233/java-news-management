package com.news_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "tblNews")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Title", unique = true)
    @Size(min = 5, max = 30, message = "Title must be between 5 and 30 characters")
    private String title;

    @Column(name = "Content")
    @Size(min = 5, max = 255, message = "Content must be between 5 and 255 characters")
    private String content;

    @ManyToOne
    @JoinColumn(name = "AuthorId")
    @JsonIgnore
    private Author author;

    @Column(name = "Created")
    private Date created;

    @Column(name = "Modified")
    private Date modified;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tblNewsTag",
            joinColumns = @JoinColumn(name = "NewsId"),
            inverseJoinColumns = @JoinColumn(name = "TagId")
    )
    @JsonIgnore
    private Set<Tag> tags;

    public News(String title, String content, Author author, Date created, Date modified, List<Comment> comments, Set<Tag> tags) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.created = created;
        this.modified = modified;
        this.comments = comments;
        this.tags = tags;
    }

    // Getter method for comments (custom property to include in JSON)
    @JsonProperty("comments")
    public List<String> getComments() {
        if(comments != null) {
            return comments.stream()
                    .map(Comment::getContent)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    // Getter method for authorName (custom property to include in JSON)
    @JsonProperty("authorName")
    public String getAuthorName() {
        return author != null ? author.getName() : null;
    }

    // Getter method for tagNames (custom property to include in JSON)
    @JsonProperty("tagNames")
    public Set<String> getTagNames() {
        if (tags != null) {
            return tags.stream()
                    .map(Tag::getName)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }
}
