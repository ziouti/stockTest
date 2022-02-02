package com.stock.service;

import com.stock.dto.CreateDto;
import com.stock.model.Bouteille;
import com.stock.model.Caisse;
import com.stock.repository.BouteilleRepository;
import com.stock.repository.CaisseRepository;
import com.stock.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CaisseService {
    private static final int NBR_BOTTLE_CAISSE = 12;
    private static final String STATUS_DAMAGED = "DAMAGED";
    private static final String STATUS_COMPLETED = "COMPLETED";
    private static final String STATUS_SHORTEGED = "SHORTEGED";

    @Autowired
    private CaisseRepository caisseRepository;
    @Autowired
    private BouteilleRepository bouteilleRepository;
    @Autowired
    private StatusRepository statusRepository;

    public Caisse saveCaisse(List<CreateDto> dtoToSave) {
  
		Integer nbrBouiteilleDamaged = dtoToSave.stream()
                .filter(dto -> dto.getEtat().equals(statusRepository.findByLibelle(STATUS_DAMAGED))
                		)
                .collect(Collectors.toList())
                .size();

        Caisse caisse = Caisse.builder()
                .idCaisse(UUID.randomUUID().toString())
                .numberBouteilleDamaged(nbrBouiteilleDamaged)
                .numberBouteilleShortaged(12 - dtoToSave.size())
                .status(getStatusCaisse(dtoToSave.size()))
                .build();

        List<Bouteille> bouiteillesToSave = dtoToSave.stream()
                .map(dto -> Bouteille.builder()
                        .idBouteille(dto.getIdBouteille())
                        .dateEnregistrment(LocalDate.now())
                        .caisse(caisse)
                        .build())
                .collect(Collectors.toList());

        caisse.setListBottles(bouiteillesToSave);

        return caisseRepository.save(caisse);
    }

    public List<Caisse> replace() {
        List<Bouteille> allBouteilleFromInComplete = new ArrayList<>();

        List<Caisse> caisseInComplete= caisseRepository.findByNumberBouteilleShortagedNot(0);
        caisseInComplete.forEach(caisse -> allBouteilleFromInComplete.addAll(caisse.getListBottles()));

        for (int i=0; i <= allBouteilleFromInComplete.size()/NBR_BOTTLE_CAISSE  ;i++){
            Caisse caisseToUpdate = caisseInComplete.get(i);

            for (int j=i*NBR_BOTTLE_CAISSE; j < (i+1)*NBR_BOTTLE_CAISSE && j < allBouteilleFromInComplete.size() ;j++){
                Bouteille bouteilleToUpdate = allBouteilleFromInComplete.get(j);

                if(!caisseToUpdate.getListBottles().contains(bouteilleToUpdate)){
                    bouteilleToUpdate.setCaisse(caisseToUpdate);
                    bouteilleRepository.save(bouteilleToUpdate);
                }
            }

            int nbrBottls = 12 - bouteilleRepository.countByCaisse(caisseToUpdate);
            
            caisseToUpdate.setStatus(getStatusCaisse(nbrBottls));
            caisseToUpdate.setNumberBouteilleShortaged(nbrBottls);

            caisseRepository.save(caisseToUpdate);
        }

        deleteEmptyCaisse();

        return findAllCaisses();
    }

    public List<Caisse> findAllCaisses() {
        return caisseRepository.findAll();
    }

    private String getStatusCaisse(int size) {
        String statusCaisse = size == NBR_BOTTLE_CAISSE ? STATUS_COMPLETED : STATUS_SHORTEGED;
        return statusCaisse;
    }

    private void deleteEmptyCaisse() {
        List<Caisse> emptyCaisses = caisseRepository.findAll().stream()
                .filter(caisse -> caisse.getListBottles() == null
                        || caisse.getListBottles().isEmpty()
                        || caisse.getListBottles().size() == 0)
                .collect(Collectors.toList());

        emptyCaisses.forEach(caisse -> caisseRepository.delete(caisse));
    }
}
