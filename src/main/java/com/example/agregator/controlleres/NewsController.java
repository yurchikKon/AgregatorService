package com.example.agregator.controlleres;

import com.example.agregator.entities.News;
import com.example.agregator.entities.Product;
import com.example.agregator.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController{

    @Autowired
    NewsService newsService;


    @GetMapping( "/news")
    public List<News> getAllNews()
    {
        return newsService.getAllNews();
    }
}
