package com.example.agregator.services;

import com.example.agregator.entities.Product;
import com.example.agregator.entities.ProductRetail;
import com.example.agregator.entities.ProductStorage;
import com.example.agregator.repositories.ProductStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ProductStorageService {
    @Autowired
    ProductStorageRepository productStorageRepository;

    public boolean isExist(String title)
    {
        List<ProductStorage> productStorages = productStorageRepository.findAll();
        for(ProductStorage p:productStorages){
            if(p.getTitle().equals(title))
                return true;
        }
        return false;
    }

    public void SaveProduct(List<Product> products, String reqName, String username)
    {
        for(Product product :products){
            if(!isExist(product.getName())){
            ProductStorage productStorage = new ProductStorage();
            productStorage.setTitle(product.getName());
            productStorage.setPrice(product.getPrice());
            productStorage.setRef(product.getRef());
            productStorage.setShop(product.getShop());
            productStorage.setImage(product.getImg());
            productStorage.setReq(reqName);
            productStorage.setUsage("+");
            productStorage.setTypeOfShop("О");
            productStorage.setLogin(username);
            productStorageRepository.save(productStorage);
            }
        }
    }
    public void SaveProductRetail(List<ProductRetail> products, String reqName, String username)
    {
        for(ProductRetail product :products){
            if(!isExist(product.getName())){
                ProductStorage productStorage = new ProductStorage();
                productStorage.setTitle(product.getName());
                productStorage.setPrice(product.getPrice());
                productStorage.setRef(product.getRef());
                productStorage.setImage(product.getImg());
                productStorage.setShop(product.getShop());
                productStorage.setReq(reqName);
                productStorage.setUsage("+");
                productStorage.setTypeOfShop("R");
                productStorage.setLogin(username);
                productStorageRepository.save(productStorage);
            }
        }
    }

    public List<ProductStorage> searchHistory(String req, String login)
    {
        return productStorageRepository.findProductStorageByReqAndLogin(req, login);
    }

    public List<ProductStorage> search(String Req, String typeOfShop, String usage)
    {
        return productStorageRepository.findProductStorageByReqAndTypeOfShopAndUsage(Req, typeOfShop, usage);
    }
    public List<ProductStorage> sortPrice(List<ProductStorage> p, int max, int min)
    {

        for(int i = 0; i < p.size(); i++)
        {
            if(Integer.parseInt(p.get(i).getPrice().replaceAll("[^\\d.]", ""))>max | Integer.parseInt(p.get(i).getPrice().replaceAll("[^\\d.]", ""))<min){
                p.remove(i);
                i--;}
        }

        return p;
    }

}
