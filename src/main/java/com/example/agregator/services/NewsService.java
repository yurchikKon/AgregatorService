package com.example.agregator.services;

import com.example.agregator.entities.News;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService {
    public void save(News news);
    public boolean isExist(String newsTitle);
    public List<News> getAllNews();
    public void clear();
}
