package com.controllers;

import com.beans.Client;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Controler {

    List<Client> clients = new ArrayList<Client>();

    public Controler(){
        clients.add(new Client(1,"el gourari","youssef"));
        clients.add(new Client(1,"el gourari","ayoub"));
        clients.add(new Client(1,"el gourari","oussama"));
    }

    @Secured("USER")
    @GetMapping("/get")
    public List<Client> getClients(){
        return clients;
    }

    @Secured("ADMIN")
    @PostMapping("/post")
    public Client addClients(@RequestBody Client client){
        clients.add(client);
        return client;
    }

    @Secured("ADMIN")
    @PutMapping("/put/{id}")
    public Client updateClient(@RequestBody Client client, @PathVariable long id){
        for (Client theClient : clients) {
            if (theClient.getId() == id) {
                theClient = client;
                break;
            }
        }
        return client;
    }

    @Secured("ADMIN")
    @DeleteMapping("/delete/{id}")
    public void adminPage(@PathVariable long id){
        for (Client theClient : clients) {
            if (theClient.getId() == id) {
                clients.remove(theClient);
                break;
            }
        }
    }
}
