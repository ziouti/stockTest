package com.stock.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Personne {

    @Id
    private String username;
    private String email;
    private String password;
    // soit 1 admin soit 0 user
    private int role;


}
