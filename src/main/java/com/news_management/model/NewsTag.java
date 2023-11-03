package com.news_management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tblNewsTag")
public class NewsTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "NewsId")
    private News news;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TagId")
    private Tag tag;

    public NewsTag(News news, Tag tag) {
        this.news = news;
        this.tag = tag;
    }
}
