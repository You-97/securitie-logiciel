package com.services;

import com.beans.Client;
import com.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class UserServices {

    private UserRepository userRepository;

    List<Client> clients = new ArrayList<Client>();

    public UserServices(){
        clients.add(new Client(1,"el gourari","youssef"));
        clients.add(new Client(1,"el gourari","ayoub"));
        clients.add(new Client(1,"el gourari","oussama"));
    }

    public List<Client> getClients(){
        return clients;
    }

    public Client addClients(Client client){
        clients.add(client);
        return client;
    }

    public Client updateClient(Client client, long id){
        for (Client theClient : clients) {
            if (theClient.getId() == id) {
                theClient = client;
                break;
            }
        }
        return client;
    }

    public void adminPage(long id){
        for (Client theClient : clients) {
            if (theClient.getId() == id) {
                clients.remove(theClient);
                break;
            }
        }
    }
}
