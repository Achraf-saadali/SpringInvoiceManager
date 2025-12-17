package org.example.springmanager2.Service;

import lombok.NoArgsConstructor;
import org.example.springmanager2.CredentialsSchema.*;
import org.example.springmanager2.Entity.Client;
import org.example.springmanager2.Entity.Comptable;
import org.example.springmanager2.Entity.Enums.ROLES;
import org.example.springmanager2.Entity.Personne;
import org.example.springmanager2.Repository.ComptableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComptableService implements CommonService {

    private final ComptableRepo comptableRepo;

    private final BCryptPasswordEncoder encoder ;

    public ComptableService(ComptableRepo comptableRepo ,@Lazy BCryptPasswordEncoder encoder) {
        this.encoder =encoder ;

        this.comptableRepo = comptableRepo;


    }


    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        return comptableRepo.findByUserEmail(userEmail);
    }

    @Override
    public ROLES supports() {
        return ROLES.COMPTABLE;
    }

    public void create(Personne personne) {
        Comptable comptable = (Comptable) personne ;
        comptable.setUserPassword(encoder.encode(comptable.getUserPassword()));

         comptableRepo.save(comptable);
    }

    public void delete(Personne comptable) {
        comptableRepo.deleteById(comptable.getUserId());
        System.out.println("comptable " + comptable + " was deleted !!!");
    }

    public  void modify(Personne fromPerson , Personne toPerson)  {
        Comptable fromComptable = (Comptable)  fromPerson ;
        Comptable toComptable = (Comptable) toPerson ;

        if (toComptable == null) {
            System.out.println("Comptable is Empty !!");
            return;
        }
        List<String> arr = checkCredentialsExchange(fromComptable, toComptable);
        fromComptable.setUserPassword(encoder.encode(arr.getLast()));
        fromComptable.setUserEmail(arr.getFirst());
        fromComptable.setUserName(arr.get(1));


        comptableRepo.save(fromComptable);
    }

    @Override
    public Authentication authentication(Authentication auth) throws BadCredentialsException {
        String userEmail = auth.getName();
        Comptable comptable = (Comptable) loadUserByUsername(userEmail);
        ExtraCredentials extraCredentials = (ExtraCredentials) auth.getCredentials();
        if (!(extraCredentials.equals(new ExtraCredentials(comptable.getUserPassword(), comptable.getComptableCode(),ROLES.COMPTABLE), encoder)))
            throw  new BadCredentialsException("password errors");

        return new UsernamePasswordAuthenticationToken
                (
                        comptable,
                        extraCredentials,
                        comptable.getAuthorities()
                );


    }
}
















