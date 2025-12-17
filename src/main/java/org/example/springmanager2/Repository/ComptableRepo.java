package org.example.springmanager2.Repository;

import org.example.springmanager2.Entity.Client;
import org.example.springmanager2.Entity.Comptable;
import org.springframework.stereotype.Repository;

@Repository
public interface ComptableRepo extends CommonRepository<Comptable,Integer> {

    public  Comptable findByUserEmailAndUserPasswordAndComptableCode( String userEmail,String userPassword,String ComptableCode);


}
