package com.stock.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cb_stock_register")
public class Caisse {

    @Id
    private String idCaisse;

    private int numberBouteilleDamaged;
    private int numberBouteilleShortaged;

    @JsonManagedReference
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "caisse")
    private List<Bouteille> listBottles;

    private String status;
}
