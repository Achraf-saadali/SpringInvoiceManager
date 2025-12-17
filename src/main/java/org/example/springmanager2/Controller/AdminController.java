package org.example.springmanager2.Controller;

import org.example.springmanager2.Service.RolesRouter;
import org.springframework.web.bind.annotation.CrossOrigin;


import org.example.springmanager2.Config.JwtUtil;
import org.example.springmanager2.Entity.Client;
import org.example.springmanager2.Service.AdminService;
import org.example.springmanager2.Service.ClientService;
import org.example.springmanager2.Service.ComptableService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/c1")
public class AdminController {

    private RolesRouter rolesRouter ;
    private JwtUtil jwtUtil ;

    @PostMapping("/client")
    public ResponseEntity<?> create(@RequestBody Client client)
    {

    }












}
