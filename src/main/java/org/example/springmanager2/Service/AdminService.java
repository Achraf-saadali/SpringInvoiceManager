package org.example.springmanager2.Service;

import lombok.Getter;
import org.example.springmanager2.Entity.Admin;
import org.example.springmanager2.Entity.Enums.ROLES;
import org.example.springmanager2.Entity.Personne;
import org.example.springmanager2.Repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Getter
@Service
public class AdminService implements CommonService
{

    private final BCryptPasswordEncoder encoder;
    private final AdminRepo adminRepo;


    @Autowired
    public AdminService(AdminRepo adminRepo ,@Lazy BCryptPasswordEncoder encoder) {
        this.encoder = encoder ;

        this.adminRepo = adminRepo;

    }


    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        return adminRepo.findByUserEmail(userEmail);
    }

    @Override
    public  ROLES supports()
    {
        return ROLES.ADMIN ;
    }

    @Override
    public void create(Personne admin){
        Admin admin2  = (Admin) admin ;
        admin2.setUserPassword(encoder.encode(admin2.getUserPassword()));
        adminRepo.save(admin2);
        System.out.println("admin was created  == "+admin2);
    }
    @Override
    public void delete(Personne admin){


        adminRepo.deleteById(admin.getUserId());
        System.out.println("admin was deleted  == "+admin);
    }
    @Override
    public  void modify(Personne fromPerson , Personne toPerson)
    {
        Admin fromAdmin = (Admin) fromPerson ;
        Admin toAdmin = (Admin) toPerson ;
        List<String> arr = checkCredentialsExchange(fromAdmin ,toAdmin);
        fromAdmin.setUserPassword(encoder.encode(arr.getLast()));

        fromAdmin.setUserEmail(arr.getFirst());
        fromAdmin.setUserName(arr.get(1));

        adminRepo.save(fromAdmin);
        System.out.println("admin "+fromAdmin+" was modified .");

    }
    @Override
    public Authentication authentication(Authentication auth) throws BadCredentialsException
    {
         String userEmail = auth.getName() ;
        System.out.println("just before loading here ...");
         Admin admin = (Admin) loadUserByUsername(userEmail) ;
        System.out.println("Admin was loaded here he is =="+admin);
        System.out.println("credentails from  authentication "+auth.getCredentials());
         String password = (String) auth.getCredentials() ;
        System.out.println("Password is encoded myabe"+ admin.getPassword());
         if(!(encoder.matches(password , admin.getPassword()))) throw new  BadCredentialsException("password errors");

         return new UsernamePasswordAuthenticationToken
                 (
                         admin ,
                         admin.getUserPassword()  ,
                         admin.getAuthorities()
                 );






    }




}
