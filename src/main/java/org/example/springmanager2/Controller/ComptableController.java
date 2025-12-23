package org.example.springmanager2.Controller;
import lombok.Data;
import org.example.springmanager2.Entity.Admin;
import org.example.springmanager2.Entity.Comptable;
import org.example.springmanager2.Entity.Enums.ROLES;
import org.example.springmanager2.Entity.Personne;
import org.example.springmanager2.Exception.InvalidTokenException;
import org.example.springmanager2.Exception.RolesException;
import org.example.springmanager2.Service.RolesRouter;
import org.springframework.web.bind.annotation.*;



import org.example.springmanager2.Config.JwtUtil;
import org.example.springmanager2.Entity.Client;



@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/comptable")
public class ComptableController {

    private RolesRouter rolesRouter;
    private  JwtUtil jwtUtil ;
    private Comptable currentComptable;


    @ModelAttribute
    public void verifyHeader(@RequestHeader("Authorization") String bearerKey) throws Exception
    {
         String token ;
         if (bearerKey == null || ! bearerKey.startsWith("Bearer ") || (token = bearerKey.substring(7)).isBlank() || ! jwtUtil.isTokenValid(token))
            throw new InvalidTokenException("Authentication required");

        currentComptable = rolesRouter.load(ROLES.COMPTABLE ,jwtUtil.extractUsername(token)) ;
        System.out.println("VALID TOKEN ");


    }
    @PostMapping("/profile")


    @PostMapping("/create-role")
    public Status create(@RequestBody Personne P) throws Exception {


         switch (P) {
            case Client C -> rolesRouter.create(C.getUserRole(), C);

            default -> throw new RolesException("Action Impossible :Can't create  this role");
        };
        return new Status(200, "Successful creation");


    }

    @PostMapping("/delete-role")
    public Status delete(@RequestBody Personne P) throws Exception {


        switch (P) {
            case Client C ->rolesRouter.delete(C.getUserRole(), C);

            default -> throw new RolesException("Action Impossible :Can't delete  this role");
        };

        return new Status(200, "Successful deletion");


    }
    @PostMapping("/modify-role")
    public Status modify(@RequestBody Personne P) throws Exception
    {
        switch (P) {
            case Client C ->rolesRouter.modify(C.getUserRole(), C);
           case  Comptable C ->rolesRouter.modify(C.getUserRole(), C);
            default -> throw new RolesException("Action Impossible :Can't modify  this role");
        };
        return new Status(200 , "Successful modification");
    }


}

