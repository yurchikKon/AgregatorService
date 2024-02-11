package com.example.agregator.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "news")
public class News {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "newsSeq", sequenceName = "news_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "newsSeq")
    public long Id;
    @Column(name = "title")
    public String title;
    @Column(name = "reference")
    public String ref;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
