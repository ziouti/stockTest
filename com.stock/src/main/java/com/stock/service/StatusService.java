package com.stock.service;

import com.stock.model.Status;
import com.stock.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    @Autowired
    private StatusRepository statusRepository;

    public List<Status> findAllStatus() {
        return statusRepository.findAll();
    }

    public Status saveStatus(Status status) {
        return statusRepository.save(status);
    }

    public Status findByLibelle(String libelle) {
        return statusRepository.findByLibelle(libelle);
    }
}
