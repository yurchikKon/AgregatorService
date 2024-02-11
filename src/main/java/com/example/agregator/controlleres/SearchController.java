package com.example.agregator.controlleres;

import com.example.agregator.entities.History;
import com.example.agregator.entities.Product;
import com.example.agregator.entities.ProductRetail;
import com.example.agregator.jobes.SearchFull;
import com.example.agregator.jobes.SearchOriginal;
import com.example.agregator.jobes.SearchRetail;
import com.example.agregator.repositories.HistoryRepository;
import com.example.agregator.repositories.ProductStorageRepository;
import com.example.agregator.services.HistoryService;
import com.example.agregator.services.ProductStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

//https://www.mvideo.ru/product-list-page/f/price=100000-429999?q=ноутбук
@Controller
public class SearchController {
    @Autowired
    HistoryService historyService;
    @Autowired
    HistoryRepository historyRepository;
    @Autowired
    ProductStorageService productStorageService;
    @GetMapping("/parser")
    public String parser(Model model, @RequestParam(value = "name1", required = false) String name1,
                         @RequestParam(value = "name2", required = false) String name2,
                         @RequestParam(value = "name3", required = false) String name3,
                         @RequestParam(value = "minPrice1", required = false) String minPrice1,
                         @RequestParam(value = "maxPrice1", required = false) String maxPrice1,
                         @RequestParam(value = "minPrice2", required = false) String minPrice2,
                         @RequestParam(value = "maxPrice2", required = false) String maxPrice2,
                         @RequestParam(value = "name4", required = false) String name4,
                         @RequestParam(value = "name5", required = false) String name5,
                         @RequestParam(value = "minPrice4", required = false) String minPrice4,
                         @RequestParam(value = "maxPrice4", required = false) String maxPrice4,
                         @CurrentSecurityContext(expression="authentication?.name") String username) throws InterruptedException, IOException {
        model.addAttribute("title", "Инструменты");
        if (name1 != null) {
            SearchOriginal.Search(model,name1, minPrice1,maxPrice1);
            int mas[] = historyService.setMinMaxPricesO(model,(List<Product>) model.getAttribute("products"));
            historyService.saveHistory(username, name1, ""+mas[0], ""+mas[1], "Обычный");
            productStorageService.SaveProduct((List<Product>) model.getAttribute("products"),name1, username);
            return "SearchResult";
        }
        if (name2 != null){
            SearchRetail.SearchR(model,name2, minPrice2, maxPrice2);
            int mas[] = historyService.setMinMaxPricesR(model,(List<ProductRetail>) model.getAttribute("products"));
            historyService.saveHistory(username, name2, ""+mas[0], ""+mas[1], "Ритейл");
            productStorageService.SaveProductRetail((List<ProductRetail>) model.getAttribute("products"),name2, username);
            return  "SearchResult";
        }
        if(name1 != null & name2 != null)
            return "error";
        if(name3 != null){
            SearchFull.SearchAll(model,name3);
            int masO[] = historyService.setMinMaxPricesO(model,(List<Product>) model.getAttribute("products"));
            int masR[] = historyService.setMinMaxPricesR(model,(List<ProductRetail>) model.getAttribute("productsR"));
            int mas[] = historyService.chek(masO,masR);
            historyService.saveHistory(username, name3, ""+mas[0], ""+mas[1], "Полный");
            productStorageService.SaveProductRetail((List<ProductRetail>) model.getAttribute("productsR"),name3, username);
            productStorageService.SaveProduct((List<Product>) model.getAttribute("products"),name3, username);
            return "SearchResult";
        }
        if(name4!=null & name5!=null) {
            if (!minPrice4.isEmpty() && !maxPrice4.isEmpty()) {
                           model.addAttribute("storageP",
                                   productStorageService.sortPrice(productStorageService.
                                           search(name4,name5,"+"),Integer.parseInt(maxPrice4),Integer.parseInt(minPrice4)));
            }
            else
                model.addAttribute("storage", productStorageService.search(name4,name5,"+"));
            return "SearchResult";
        }
        return "parser";
    }
}
