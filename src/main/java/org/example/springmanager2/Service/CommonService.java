package org.example.springmanager2.Service;

import org.example.springmanager2.Config.JwtUtil;
import org.example.springmanager2.Entity.Enums.ROLES;
import org.example.springmanager2.Entity.Personne;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public interface CommonService  extends UserDetailsService
{






        public Authentication authentication(Authentication auth)  throws AuthenticationCredentialsNotFoundException;

        default  List<String> checkCredentialsExchange(Personne person1 , Personne person2)
        {      if (person1 == null) throw new IllegalArgumentException("person1 cannot be null");

            if (person2 == null) {
                return List.of(person1.getUsername(), person1.getUserName(), person1.getUserPassword());
            }

            String userEmail = (person2.getUsername() == null || person2.getUsername().isBlank())
                    ? person1.getUsername() : person2.getUsername();
            String userName = (person2.getUserName() == null || person2.getUserName().isBlank())
                    ? person1.getUserName() : person2.getUserName();
            String userPassword = (person2.getUserPassword() == null || person2.getUserPassword().isBlank())
                    ? person1.getUserPassword() : person2.getUserPassword();

            return List.of(userEmail, userName, userPassword);
        }




        public   ROLES supports();

        public  void create(Personne person) ;

        public  void delete(Personne person) ;

        public  void modify(Personne fromPerson , Personne toPerson) ;






        }
