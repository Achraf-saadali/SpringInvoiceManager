package org.example.springmanager2.Controller;


import lombok.Data;
import org.example.springmanager2.Config.JwtUtil;
import org.example.springmanager2.CredentialsSchema.*;
import org.example.springmanager2.Entity.Admin;
import org.example.springmanager2.Entity.Client;
import org.example.springmanager2.Entity.Comptable;
import org.example.springmanager2.Entity.Enums.ROLES;
import org.example.springmanager2.Service.AdminService;
import org.example.springmanager2.Service.ClientService;
import org.example.springmanager2.Service.ComptableService;

import org.example.springmanager2.Service.RolesRouter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("http://localhost:3000")

@RestController
@RequestMapping("/auth/login")
public class AuthController {

    private final RolesRouter rolesRouter;

    private final JwtUtil jwtUtil ;
    private final AuthenticationManager authenticationManager;



    public AuthController(RolesRouter rolesRouter, AuthenticationManager authenticationManager, JwtUtil jwtUtil)
    {

        this.jwtUtil = jwtUtil ;
        this.authenticationManager = authenticationManager;
        this.rolesRouter = rolesRouter;
    }

    @PostMapping("/admin")
    public ResponseEntity<?> loginAdmin(@RequestBody Admin admin )
    {

        System.out.println("our admin is =="+admin );

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(admin.getUserEmail(),admin.getUserPassword() ) );

        // If authentication succeeds → generate JWT
        String token = jwtUtil.generateToken(admin.getUserEmail(), ROLES.ADMIN);
        return ResponseEntity.ok(new AuthResponse(token,ROLES.ADMIN));



    }
    @PostMapping("/client")
    public ResponseEntity<?> loginClient(@RequestBody Client client )
    {
       ExtraCredentials clientpasswords = new ExtraCredentials(client.getPassword(),client.getClientCode(),ROLES.CLIENT);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(client.getUserEmail(),clientpasswords )
        );


        String token = jwtUtil.generateToken(client.getUserEmail(),ROLES.CLIENT);
        return ResponseEntity.ok(new AuthResponse(token,ROLES.CLIENT));


    }



    @PostMapping("/Posttest")
    public String POSTTEST(@RequestBody Admin admin )
    {

        return null;
    }


    @PostMapping("/comptable")
    public ResponseEntity<?> loginComptable(@RequestBody Comptable comptable )
    {



        ExtraCredentials comptablepasswords = new ExtraCredentials(comptable.getPassword(),comptable.getComptableCode(),ROLES.COMPTABLE);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(comptable.getUserEmail(),comptablepasswords )
        );

        // If authentication succeeds → generate JWT
        String token = jwtUtil.generateToken(comptable.getUserEmail(),ROLES.COMPTABLE);
        return ResponseEntity.ok(new AuthResponse(token,ROLES.COMPTABLE));
    }



@Data
class AuthResponse {

        private String token ;
        private ROLES role ;

        public AuthResponse(String token, ROLES role)
        {
            this.token = token ;
            this.role =role;
        }
}




}
