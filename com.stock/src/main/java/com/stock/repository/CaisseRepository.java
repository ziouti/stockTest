package com.stock.repository;

import com.stock.model.Caisse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CaisseRepository extends JpaRepository<Caisse, String> {

    List<Caisse> findByNumberBouteilleShortagedNot(int numberBouteilleShortaged);


    @Query(value = "select * from cb_stock_register where id_caisse not in (select distinct(id_caisse) from cb_bottle_register)", nativeQuery = true)
    List<Caisse> findAllCaisseWithouBotelles();
}