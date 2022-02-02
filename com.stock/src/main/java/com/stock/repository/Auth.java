package com.stock.repository;

import com.stock.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Auth extends JpaRepository<Personne, String> {
    Personne findByUsername(String username);

}
