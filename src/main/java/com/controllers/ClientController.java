package com.controllers;

import com.beans.Client;
import com.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientRepository service;

    @Autowired
    ClientController(ClientRepository service){
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") long id){
        Optional<Client> data = service.findById(id);

        return data.map(client -> new ResponseEntity<>(client, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<Client> createClient(@RequestBody Client client){
        System.out.println("youssef");
        try {
            Client _client = service.save(client);
            return new ResponseEntity<>(_client, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateTutorial(@PathVariable("id") long id, @RequestBody Client client) {
        Optional<Client> clientData = service.findById(id);

        if (clientData.isPresent()) {
            Client _client = clientData.get();
            _client.setNom(client.getNom());
            _client.setPrenom(client.getPrenom());
            return new ResponseEntity<>(service.save(_client), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable("id") long id){
        try {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAllClients(){
        try {

            List<Client> clients = new ArrayList<Client>(service.findAll());

            if (clients.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
