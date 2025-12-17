package org.example.springmanager2.Controller;


import org.example.springmanager2.Entity.Admin;
import org.example.springmanager2.Entity.Client;
import org.example.springmanager2.Entity.Comptable;
import org.example.springmanager2.Entity.Personne;
import org.example.springmanager2.Service.AdminService;
import org.example.springmanager2.Service.ClientService;
import org.example.springmanager2.Service.ComptableService;

import org.springframework.web.bind.annotation.*;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/auth/login")
public class UserController {

    private final AdminService adminService ;

    private final ClientService clientService;

    private final ComptableService comptableService ;

    public UserController(AdminService adminService,ClientService clientService , ComptableService comptableService)
    {
        this.adminService = adminService ;
        this.clientService = clientService ;
        this.comptableService =comptableService ;
    }

    @PostMapping("/admin")
    public Admin loginAdmin(@RequestBody Admin admin )
    {
        System.out.println("this is the admin here : "+admin);
        Admin adminResponse = adminService.findByUserEmailAndUserPassword(admin.getUserEmail(),admin.getUserPassword());
        System.out.println(adminResponse);
        return adminResponse ;
    }
    @PostMapping("/client")
    public Client loginClient(@RequestBody Client client )
    {   Client clientResponse = clientService.findByUserEmailAndUserPasswordAndCodeClient(client.getUserEmail(),client.getUserPassword(),client.getClientCode());

        System.out.println(clientResponse);
        return clientResponse;
    }
    @PostMapping("/Posttest")
    public String POSTTEST(@RequestBody Admin admin )
    {
        System.out.println("test works");
        return "POST TEST HERE ";
    }
    @PostMapping("/comptable")
    public Comptable loginComptable(@RequestBody Comptable comptable )
    {
        System.out.println(comptable);
        return comptable;
    }

    @GetMapping("/getMapping")
  public String getAll(){
        return "this is all there is ";
    }
}
