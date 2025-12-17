package org.example.springmanager2.Service;

import org.example.springmanager2.Entity.Personne;
import org.example.springmanager2.Repository.AdminRepo;
import org.example.springmanager2.Repository.ClientRepo;
import org.example.springmanager2.Repository.ComptableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService


{
    private final ClientRepo clientRepo ;
    private final AdminRepo adminRepo ;

    private ComptableRepo comptableRepo;


    public UserService(ClientRepo clientRepo){
        this.clientRepo = clientRepo ;

    }
    public UserService(AdminRepo adminRepo){
        this.adminRepo = adminRepo;
    }

    public UserService(ComptableRepo comptableRepo){
        this.comptableRepo = comptableRepo ;
    }

    @Autowired
    public UserService(ComptableRepo comptableRepo) {
        this.comptableRepo = comptableRepo;
    }

    public Comptable findByUserEmailAndUserPasswordAndCodeComptable(String userEmail ,String userPassword , String codeComptable){

        if(comptableRepo != null) return comptableRepo.findByUserEmailAndUserPasswordAndComptableCode(userEmail ,userPassword ,codeComptable) ;

        return  null;



    }

    public Client findByUserEmailAndUserPasswordAndCodeClient(String userEmail ,String userPassword , String codeClient){
        if(clientRepo != null) return clientRepo.findByUserEmailAndUserPasswordAndClientCode(userEmail ,userPassword ,codeClient);
        return null;
    }

    public Admin findByUserEmailAndUserPasswordAndCodeClient(String userEmail ,String userPassword){
        if(adminRepo != null) return adminRepo.findByUserEmailAndUserPassword(userEmail , usrPassword);
        return null
    }








    public static void main(String[] args)
    {

    }
}
