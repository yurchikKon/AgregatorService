package com.example.agregator.jobes;

import com.example.agregator.entities.ProductRetail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SearchRetail {
    public static List<ProductRetail> products = new ArrayList<>();

    public static void SearchR(Model model, String name, String min, String max) throws InterruptedException, IOException {
        String urlY;
        if (min.isEmpty() || max.isEmpty()) {
            urlY = "https://youla.ru/moskva?q=" + name;
        } else {
            urlY = "https://youla.ru/moskva?attributes[price][to]=" + max + "00" + "&attributes[price][from]=" +min+"00"+ "&q=" + name;
        }
        products.clear();
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\yura0\\Downloads\\agregator\\agregator\\src\\main\\resources\\driveres\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--start-fullscreen");
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--user-agent=Mozilla/5.0 (X11\\\\; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36");
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        Document doc;
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        if (!min.isEmpty() & !max.isEmpty()) {
            webDriver.get("https://www.avito.ru/all?q=" + name);
            Thread.sleep(3000);
            List<WebElement> wel = webDriver.findElements(By.className("input-input-Zpzc1"));
            wel.get(2).sendKeys(min);
            wel.get(3).sendKeys(max);
            webDriver.findElement(By.className("button-primary-x_x8w")).click();

            js.executeScript("document.body.style.zoom='10%'");
            Thread.sleep(3000);

            doc = Jsoup.parse(webDriver.getPageSource());
        } else {
            try {
                doc = Jsoup.connect("https://www.avito.ru/all?q=" + name).get(); //pars avito
            } catch (IOException e) {
                doc = Jsoup.connect("https://www.avito.ru/all?q=" + name).get();
            }
        }
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
//https://youla.ru/moskva?attributes[price][to]=1111100&attributes[price][from]=100000&q=ноутбук
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
                products.add(obj);
            }
            for (int i = 0; i < elAmN.size(); i++) {
                ProductRetail obj = new ProductRetail();
                obj.setName(elAmN.get(i).ownText());
                obj.setRef("https://www.amazon.com" + elAmR.get(i).attr("href"));
                obj.setPrice("?");
                obj.setImg(elAmI.get(i).attr("src"));
                obj.setShop("Амазон");
                products.add(obj);
            }
            for (int i = 0; i < elYI.size(); i++) {
                ProductRetail obj = new ProductRetail();
                obj.setName(elYNaR.get(i).attr("title"));
                obj.setOtherInf(elYG.get(i).ownText());
                obj.setRef("https://youla.ru" + elYNaR.get(i).attr("href"));
                obj.setPrice(elYP.get(i).ownText());
                obj.setShop("Юла");
                obj.setImg(elYI.get(i).attr("xlink:href"));
                products.add(obj);
            }
            System.out.println(products.get(0).getImg());
            model.addAttribute("products", products);
            webDriver.close();
            webDriver.quit();
        }
    }
