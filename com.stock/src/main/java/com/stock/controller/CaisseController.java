package com.stock.controller;

import com.stock.dto.CreateDto;
import com.stock.model.Caisse;
import com.stock.service.CaisseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caisse")
public class CaisseController {
    private static final int NBR_BOTTLE_CAISSE = 12;

    @Autowired
    private CaisseService caisseService;

    @PostMapping
    public Caisse saveCaisse(@RequestBody List<CreateDto> dtoToSave) throws Exception {

        if(dtoToSave.size() > NBR_BOTTLE_CAISSE) {
            throw new RuntimeException("Vous devez m'nevoyer un pacquet de 12 bouteille et pas plus!");
        }
        return caisseService.saveCaisse(dtoToSave);
    }

    @PutMapping
    public List<Caisse> replace() {
        return caisseService.replace();
    }

    @GetMapping("/getCaisse")
    
    public List<Caisse> findAllCaisses() {
        return caisseService.findAllCaisses();
    }
}
