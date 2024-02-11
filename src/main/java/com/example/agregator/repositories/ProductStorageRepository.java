package com.example.agregator.repositories;

import com.example.agregator.entities.ProductStorage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductStorageRepository extends JpaRepository<ProductStorage,Long> {
    List<ProductStorage> findProductStorageByReqAndTypeOfShopAndUsage(String req, String typeOfShop, String usage);
    List<ProductStorage> findProductStorageByReqAndLogin(String req, String login);

    @Modifying
    @Transactional
    @Query("update ProductStorage set usage = ?1")
    default void upMinUsage(String minus) {

    }

    @Modifying
    @Transactional
    @Query("update ProductStorage set usage = ?1 where req = ?2")
    void upPlusUsage(String plus, String req);

}
