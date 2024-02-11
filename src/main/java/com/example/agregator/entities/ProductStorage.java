package com.example.agregator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ProductStorage")
@Setter
@Getter
public class ProductStorage {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "req")
    private String req;
    @Column(name = "title")
    private String title;
    @Column(name = "ref")
    private String ref;
    @Column(name = "image")
    private String image;
    @Column(name = "shop")
    private String shop;
    @Column(name = "price")
    private String price;
    @Column(name = "usage")
    private String usage;
    @Column(name = "typeOfShop")
    private String typeOfShop;
    @Column(name = "login")
    private  String login;


}
