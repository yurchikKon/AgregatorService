package com.example.agregator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="usr_history")
@Setter
@Getter
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "ULogin")
    public String ULogin;
    @Column(name = "historyName")
    public String historyName;
    @Column(name = " minPrice")
    public String minPrice;
    @Column(name = "maxPrice")
    public String maxPrice;
    @Column(name = "tSearch")
    public String tSearch;
}
