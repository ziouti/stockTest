package com.stock.repository;

import com.stock.model.Bouteille;
import com.stock.model.Caisse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BouteilleRepository extends JpaRepository<Bouteille, String> {

    int countByCaisse(Caisse caisse);
}
