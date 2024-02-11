package com.example.agregator.controlleres;

import com.example.agregator.entities.History;
import com.example.agregator.entities.News;
import com.example.agregator.jobes.SearchFull;
import com.example.agregator.jobes.SearchOriginal;
import com.example.agregator.jobes.SearchRetail;
import com.example.agregator.repositories.HistoryRepository;
import com.example.agregator.repositories.NewsRepository;
import com.example.agregator.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class MainController {
    @Autowired
    private NewsRepository newsRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "mainpage";
    }

    @GetMapping("/info")
    public String info(Model model) {
        model.addAttribute("title", "Новости");
        Iterable<News> news = newsRepository.findAll();
        model.addAttribute("news", news);
        return "info";
    }


    @GetMapping("/base")
    public String base(Model model) {
        model.addAttribute("title", "База");
        return "base";
    }
}
