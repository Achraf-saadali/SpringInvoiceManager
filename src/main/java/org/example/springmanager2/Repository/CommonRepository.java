package org.example.springmanager2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface CommonRepository<ENTITY,ID> extends JpaRepository<ENTITY,ID> {

    public ENTITY findByUserEmailAndUserPassword
            ( String userEmail,String userPassword);

    public ENTITY findByUserEmail(String userEmail);
    public ENTITY deleteByUserEmail(String userEmail);

}
