package com.example.agregator.repositories;

import com.example.agregator.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface HistoryRepository extends JpaRepository<History,Long> {

    Iterable<History> findHistoryByULogin(String login);

}
