package com.example.esercitazione.controller;

import com.example.esercitazione.domain.Corso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.esercitazione.repository.CorsoRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class CorsoController {
    @Autowired
    CorsoRepository corsoRepository;

    @GetMapping("/getcorso")
    public ResponseEntity<Corso>getCorso(){
        Corso corso = new Corso(1L,"algebra lineare","primo anno");
        return new ResponseEntity<Corso>(corso,HttpStatus.OK);
    }


    @PostMapping("/corso")
    public ResponseEntity<?> creaCorso(@RequestBody Corso corso) {
        List<Corso> corsi = corsoRepository.findAll();
        HashMap<String, String> hash = new HashMap<>();
        hash.put("Error", "duplicate");
        for (Corso c : corsi) {
            if (c.getId() == corso.getId()) {
                return new ResponseEntity<>(hash, HttpStatus.OK);
            }
        }
        corsoRepository.save(corso);
        return new ResponseEntity<>(corso, HttpStatus.CREATED);
    }





    @PutMapping("/corso/{id}")
    public ResponseEntity<?> updatecorso(@PathVariable("id") long id_corso,@RequestBody Corso corso) {
        Corso corso1 = new Corso( 2L,"Fisica1","primo anno");
        if(corso1.getId() == id_corso)
            return new ResponseEntity<>(corso1, HttpStatus.CREATED);
        return new ResponseEntity<>(corso,HttpStatus.NO_CONTENT); }

    @DeleteMapping("/corso/{id}")
    public ResponseEntity<?> deletecorso(@PathVariable long id){
        System.out.println("Corso:" + id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getCorso3/{id}/{campo}")public ResponseEntity <?> findOneCampo(long id, String campo) {
        final Corso[] c = new Corso[1];
        corsoRepository.findAll().forEach(course -> {
            if (course.getId() == id) {
                c[0] = course;
            }
        });
        if (c[0] == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity <> ((campo.equalsIgnoreCase("descrizione")) ?
                c[0].getDescrizione() : c[0].getNome(), HttpStatus.OK);
    }
    @PostMapping("/corso1")public ResponseEntity<?> creaCorso1(@RequestBody Corso corso) {
        Corso corso1 = corsoRepository.save(corso);
        if (corso.getNome().contains("corso_") && corso.getNome().length() > 8) {
            return new ResponseEntity<>(corso, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(corso, HttpStatus.NO_CONTENT);

    }

    @GetMapping("/getNumeroCorsiTB")public ResponseEntity <?> getNumeroCorsiTB() {
        int i = 0;
        for (Corso corso : corsoRepository.findAll())
            if (corso.getNome().toLowerCase().contains("corso_")) i++;
        return new ResponseEntity <> (i, HttpStatus.OK);
    }


    @GetMapping("/getLunghezza")
    public ResponseEntity<?> getLunghezza() {
        List<Corso> list = new ArrayList<>();
        for (Corso corso : corsoRepository.findAll())
            if (corso.getDescrizione() != null && corso.getDescrizione().length() < 20) list.add(corso);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }











}


