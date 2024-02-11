package com.example.agregator.jobes;

import com.example.agregator.services.NewsService;
import com.example.agregator.entities.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ParseTask {
    @Autowired
    NewsService newsService;
    @Scheduled(fixedDelay = 10000)
    public void parseNews()
    {
        String url = "https://www.kommersant.ru/theme/1079";
        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla").timeout(5000).referrer("https://google.com").get();
            Elements news = doc.getElementsByClass("uho__name rubric_lenta__item_name").select("a");
            for(Element el:news){
                String title = el.ownText();
                if(!newsService.isExist(title) && !title.isEmpty()){
                    String ref = "https://www.kommersant.ru" + news.attr("href");
                     News obj = new News();
                     obj.setTitle(title);
                     obj.setRef(ref);
                     newsService.save(obj);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Scheduled(fixedDelay = 1000000)
    public void clear()
    {
        newsService.clear();
    }
}
