package org.example.springmanager2.Service;

import org.example.springmanager2.CredentialsSchema.*;
import org.example.springmanager2.Entity.Admin;
import org.example.springmanager2.Entity.Client;
import org.example.springmanager2.Entity.Comptable;
import org.example.springmanager2.Entity.Enums.ROLES;
import org.example.springmanager2.Entity.Personne;
import org.example.springmanager2.Repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements CommonService  {

   private final BCryptPasswordEncoder encoder ;


    private final ClientRepo clientRepo;

     @Autowired
     public ClientService(ClientRepo clientRepo,@Lazy BCryptPasswordEncoder encoder) {
        this.encoder = encoder ;

        this.clientRepo = clientRepo;


    }



    @Override
    public ROLES supports() {
        return ROLES.CLIENT;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        return clientRepo.findByUserEmail(userEmail);

    }
    @Override
    public void create(Personne client) {
        Client client2 = (Client) client ;
        client2.setUserPassword(encoder.encode(client2.getUserPassword()));
        clientRepo.save(client2);
    }
    @Override
    public void delete(Personne client) {

        clientRepo.deleteById(client.getUserId());
        System.out.println("client " + client + "was deleted !!!");
    }
    @Override
    public  void modify(Personne fromPerson , Personne toPerson)  {
        Client toClient = (Client)  toPerson ;
        Client fromClient = (Client) fromPerson ;
        if (toClient == null) {
            System.out.println("Client is Empty !!");
            return;
        }
        List<String> arr = checkCredentialsExchange(fromClient, toClient);

        fromClient.setUserPassword(encoder.encode(arr.getLast()));
        fromClient.setUserEmail(arr.getFirst());
        fromClient.setUserName(arr.get(1));


         clientRepo.save(fromClient);


    }

    @Override
    public Authentication authentication(Authentication auth) throws AuthenticationCredentialsNotFoundException {
        String userEmail = auth.getName();
        Client client = (Client) loadUserByUsername(userEmail);

        ExtraCredentials extraCredentials = (ExtraCredentials) auth.getCredentials();
        if (!(extraCredentials.equals(new ExtraCredentials(client.getUserPassword(), client.getClientCode(),ROLES.CLIENT), encoder)))
            throw new BadCredentialsException("password errors");

        return new UsernamePasswordAuthenticationToken
                (
                        client,
                        extraCredentials,
                        client.getAuthorities()
                );


    }

}

