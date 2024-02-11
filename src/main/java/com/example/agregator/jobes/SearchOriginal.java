package com.example.agregator.jobes;

import com.example.agregator.entities.Product;
import com.example.agregator.entities.ProductStorage;
import com.example.agregator.services.ProductStorageService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchOriginal {
    public static List<Product> products = new ArrayList<>();
    public static void Search(Model model,String name, String min, String max) throws  InterruptedException {

        products.clear();
        System.out.println("hello");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Documents\\петы\\agregator\\agregator\\src\\main\\resources\\driveres\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--start-fullscreen");
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--user-agent=Mozilla/5.0 (X11\\\\; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36");
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        String urlM;
        String urlC;
        if (min.isEmpty() || max.isEmpty()){
            urlM = "https://www.mvideo.ru/product-list-page?q="+name;
            urlC = "https://www.citilink.ru/search/?text=" +name;}
        else{
            urlM = "https://www.mvideo.ru/product-list-page/f/price=" + min +"-"+max+"?q="+name;
            urlC = "https://www.citilink.ru/search/?text=" + name +"&price_min=" + min + "&price_max=" + max;
        }
        webDriver.get(urlM); //https://www.citilink.ru/search/?text=ноутбук&price_min=50000&price_max=457490
        System.out.println("connection success");
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("document.body.style.zoom='10%'");
        Thread.sleep(5000);
        Document doc = Jsoup.parse(webDriver.getPageSource());
        Elements elements = doc.getElementsByClass("product-title--grid").select("a");
        Elements el = doc.getElementsByClass("price__main-value");
        Elements elI = doc.getElementsByClass("product-picture__img--grid");
        Elements elII = new Elements();
        String chek = new String();
        for(Element e:elI)
        {
            if(!chek.equals(e.attr("alt"))){
                elII.add(e);
                chek = e.attr("alt");
            }

        }
        webDriver.get(urlC);
        System.out.println("connection success");
        js.executeScript("document.body.style.zoom='10%'");
        Thread.sleep(5000);
        elI.clear();
        chek = " ";
        Document docE = Jsoup.parse(webDriver.getPageSource());
        Elements CiELImage = docE.getElementsByClass("e153n9o30").select("img");
        for(Element e:CiELImage)
        {
            if(!chek.equals(e.attr("alt"))){
                elI.add(e);
                chek = e.attr("alt");
            }

        }
        Elements CiElName = docE.getElementsByClass("app-catalog-oacxam").select("a");
        Elements CiElPrice = docE.getElementsByClass("app-catalog-175fskm");
        for(int i = 0; i < elements.size(); i++)
        {
            Product obj  = new Product();
            obj.setName(elements.get(i).ownText());
            obj.setPrice(el.get(i).ownText());
            obj.setRef("https://www.mvideo.ru" + elements.get(i).attr("href"));
            obj.setImg(elII.get(i).attr("src"));
            obj.setShop("Мвидео");
            products.add(obj);
        }
        for (int i = 0; i < CiElName.size();i++)
        {
            Product objE = new Product();
            objE.setName(CiElName.get(i).ownText());
            objE.setPrice(CiElPrice.get(i+1).ownText());
            objE.setImg(elI.get(i).attr("src"));
            objE.setRef("https://www.citilink.ru" + CiElName.get(i).attr("href"));
            objE.setShop("Ситилинк");
            products.add(objE);
        }
        model.addAttribute("products",products);
        webDriver.close();
        webDriver.quit();
    }

}
