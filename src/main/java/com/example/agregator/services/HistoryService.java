package com.example.agregator.services;

import com.example.agregator.entities.History;
import com.example.agregator.entities.Product;
import com.example.agregator.entities.ProductRetail;
import com.example.agregator.entities.ProductStorage;
import com.example.agregator.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class HistoryService {
    @Autowired
    HistoryRepository historyRepository;

    public void saveHistory(String login, String title,String max, String min,String tSearch)
    {
        History history = new History();
        history.setULogin(login);
        history.setHistoryName(title);
        history.setMaxPrice(max);
        history.setMinPrice(min);
        history.setTSearch(tSearch);
        historyRepository.save(history);
    }
    public int[] setMinMaxPricesO(Model model, List <Product> l)
    {
        int min = 1000000000;
        int max = 0;
        for(int i = 0; i < l.size(); i++)
        {
            if(Integer.parseInt(l.get(i).getPrice().replaceAll("[^\\d.]", "")) > max)
                max = Integer.parseInt(l.get(i).getPrice().replaceAll("[^\\d.]", ""));
            else if (Integer.parseInt(l.get(i).getPrice().replaceAll("[^\\d.]", ""))< min)
                min = Integer.parseInt(l.get(i).getPrice().replaceAll("[^\\d.]", ""));
        }
        int mas[] = {max,min};
        return mas;
    }
    public int[] setMinMaxPricesR(Model model, List <ProductRetail> l)
    {
        int min = 1000000000;
        int max = 0;
        for(int i = 0; i < l.size(); i++)
        {
            if (!l.get(i).getPrice().replaceAll("[^\\d.]", "").isEmpty()){
                if(Integer.parseInt(l.get(i).getPrice().replaceAll("[^\\d.]", "")) > max)
                    max = Integer.parseInt(l.get(i).getPrice().replaceAll("[^\\d.]", ""));
                else if (Integer.parseInt(l.get(i).getPrice().replaceAll("[^\\d.]", ""))< min)
                    min = Integer.parseInt(l.get(i).getPrice().replaceAll("[^\\d.]", ""));
            }
        }
        int mas[] = {max,min};
        return mas;
    }
    public int[] chek(int[] masO, int[] masR)
    {
        int mas[] = {0,0};
        if (masO[0]>masR[0])
            mas[0] = masO[0];
        else if (masO[0]<masR[0])
            mas[0] = masR[0];
        else
            mas[0] = masO[0];
        if (masO[1]>masR[1])
            mas[1] = masR[1];
        else if (masO[1]<masR[1])
            mas[1] = masO[1];
        else
            mas[1] = masO[1];
        return mas;
    }
}
