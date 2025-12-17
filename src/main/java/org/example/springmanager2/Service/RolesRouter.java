package org.example.springmanager2.Service;

import lombok.Getter;
import org.example.springmanager2.Entity.Enums.ROLES;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.example.springmanager2.Entity.Personne;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
public class RolesRouter
{

   private Map<ROLES,CommonService> mappedServices  ;


   public RolesRouter(List<CommonService> commonServices){
       this.mappedServices = new HashMap<>();

       for(CommonService service  : commonServices ) mappedServices.put(service.supports() , service) ;

   }

   public Authentication authenticate(ROLES role , Authentication auth)
   {
       return mappedServices.get(role).authentication(auth);
   }

   public void create(ROLES role , Personne P)     {
       mappedServices.get(role).create( P);
   }

    public void delete(ROLES role , Personne P)     {
        mappedServices.get(role).delete( P);
    }
    public void modify(ROLES role , Personne P1 ,Personne P2)
    {
        mappedServices.get(role).modify( P1, P2);
    }


    public   UserDetails load(ROLES role , String userEmail)
    {
        return  mappedServices.get(role).loadUserByUsername(userEmail);
    }






}
