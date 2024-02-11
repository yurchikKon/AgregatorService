package com.example.agregator.jobes;
import com.example.agregator.entities.Product;
import com.example.agregator.entities.ProductRetail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SearchFull {
    public static List<Product> products = new ArrayList<>();
    public static List<ProductRetail> productsR = new ArrayList<>();
    public static void SearchAll(Model model, String name) throws InterruptedException, IOException {
        products.clear();
        productsR.clear();
        System.out.println("hello");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\yura0\\Downloads\\agregator\\agregator\\src\\main\\resources\\driveres\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--start-fullscreen");
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--user-agent=Mozilla/5.0 (X11\\\\; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36");
        WebDriver webDriver = new ChromeDriver(chromeOptions);

        webDriver.get("https://www.mvideo.ru/product-list-page?q=" + name);
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
        webDriver.get("https://www.citilink.ru/search/?text=" + name);
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
        model.addAttribute("products", products);
        doc = Jsoup.connect("https://www.avito.ru/all?q=" + name).get(); //pars avito
        Elements elAI = doc.getElementsByClass("photo-slider-list-item-h3A51").select("div").select("img");
        Elements elAN = doc.getElementsByClass("iva-item-titleStep-pdebR").select("div")
                .select("a");
        Elements elAP = doc.getElementsByClass("styles-module-root-LIAav").select("span");
        Elements elAG = doc.getElementsByClass("styles-module-noAccent-nZxz7");
        Elements elAR = doc.getElementsByClass("iva-item-titleStep-pdebR").select("a"); //end pars

        try {
            doc = Jsoup.connect("https://www.amazon.com/s?k=" + name).get(); //pars amazon
        } catch (IOException e) {
            doc = Jsoup.connect("https://www.amazon.com/s?k=" + name).get();
        }
        Elements elAmI = doc.getElementsByClass("s-image");
        Elements elAmN = doc.getElementsByClass("s-title-instructions-style").select("h2")
                .select("a").select("span");
        Elements elAmR = doc.getElementsByClass("s-title-instructions-style").select("h2")
                .select("a"); // end pars
        String urlY = "https://youla.ru/moskva?q=" + name;
        webDriver.get(urlY); //pars youla
        System.out.println("connection success");

        js.executeScript("document.body.style.zoom='10%'");
        Thread.sleep(10000);
        doc = Jsoup.parse(webDriver.getPageSource());
        Elements elYI = doc.getElementsByClass("ipuCdo").select("image");
        Elements elYNaR = doc.getElementsByClass("fEAASo").select("a");
        Elements elYG = doc.getElementsByClass("heJjNv").select("div").select("div").select("div")
                .select("div").select("span");
        Elements elYP = doc.getElementsByClass("iMealh"); // end pars
        for (int i = 0; i < elAI.size(); i++) {
            ProductRetail obj = new ProductRetail();
            obj.setName(elAN.get(i).attr("title"));
            obj.setPrice(elAP.get(i).ownText());
            obj.setOtherInf(elAG.get(i).ownText());
            obj.setRef("https://www.avito.ru" + elAR.get(i).attr("href"));
            obj.setImg(elAI.get(i).attr("src"));
            obj.setShop("Авито");
            productsR.add(obj);
        }
        for (int i = 0; i < elAmN.size(); i++) {
            ProductRetail obj = new ProductRetail();
            obj.setName(elAmN.get(i).ownText());
            obj.setRef("https://www.amazon.com" + elAmR.get(i).attr("href"));
            obj.setPrice("?");
            obj.setImg(elAmI.get(i).attr("src"));
            obj.setShop("Амазон");
            productsR.add(obj);
        }
        for (int i = 0; i < elYI.size(); i++) {
            ProductRetail obj = new ProductRetail();
            obj.setName(elYNaR.get(i).attr("title"));
            obj.setOtherInf(elYG.get(i).ownText());
            obj.setRef("https://youla.ru" + elYNaR.get(i).attr("href"));
            obj.setPrice(elYP.get(i).ownText());
            obj.setShop("Юла");
            obj.setImg(elYI.get(i).attr("xlink:href"));
            productsR.add(obj);
        }
        model.addAttribute("productsR",productsR);
        webDriver.quit();
    }
}
