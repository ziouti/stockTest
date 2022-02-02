package com.stock.controller;

import com.stock.model.Status;
import com.stock.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping
    public List<Status> findAllStatus(){
        return statusService.findAllStatus();
    }

    @GetMapping("/search")
    public Status findByLibelle(@RequestParam String libelle){
        return statusService.findByLibelle(libelle);
    }

    @PostMapping
    public Status saveStatus(@RequestBody  Status status){
        return statusService.saveStatus(status);
    }
}
