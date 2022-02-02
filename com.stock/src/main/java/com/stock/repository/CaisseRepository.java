package com.stock.repository;

import com.stock.model.Caisse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CaisseRepository extends JpaRepository<Caisse, String> {

    List<Caisse> findByNumberBouteilleShortagedNot(int numberBouteilleShortaged);
}
