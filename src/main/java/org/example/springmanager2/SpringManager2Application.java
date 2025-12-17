package org.example.springmanager2;

import org.example.springmanager2.Entity.Admin;
import org.example.springmanager2.Entity.Client;
import org.example.springmanager2.Repository.AdminRepo;
import org.example.springmanager2.Repository.ClientRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringManager2Application {

    public static void main(String[] args) {



        ApplicationContext container  = SpringApplication.run(SpringManager2Application.class, args);

        String[] beans = container.getBeanDefinitionNames();

        int howManyBeans = beans.length ;

        System.out.println("***********************************************************************************************************************");
        for (int i = 0; i < howManyBeans; i++) {


            System.out.println("bean number  = "+i+" is : "+beans[i]);

        }
        System.out.println("***********************************************************************************************************************");
//
//         ClientRepo adminrepo = container.getBean(ClientRepo.class);
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        Client admin2 = new Client();
//            admin2.setUserEmail("achrafsaadaliiiiiii@gmail.com");
//            admin2.setUserPassword(encoder.encode("Ach.....2003"));
//            admin2.setUserName("Achraf__2003");
//            admin2.setClientCode("Cl-2003");
//
//            adminrepo.save(admin2);
//            System.out.println("admin is "+admin2.getUserId());
    }

}
