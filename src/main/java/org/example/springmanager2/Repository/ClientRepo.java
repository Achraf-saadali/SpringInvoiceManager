package org.example.springmanager2.Repository;

import org.example.springmanager2.Entity.Client;
import org.springframework.stereotype.Repository;
@Repository
public interface ClientRepo  extends CommonRepository<Client,Integer>
{
    public Client findByUserEmailAndUserPasswordAndClientCode( String userEmail,String userPassword,String ClientCode);
}
