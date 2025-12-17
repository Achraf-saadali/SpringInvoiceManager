package org.example.springmanager2.CredentialsSchema;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
@Setter
@Getter

public class ExtraCredentials {
    private String userPassword ;
    private String additionalCode ;


    public boolean equals(Obj ob ,BCryptPasswordEncoder encoder)
    {
        if (this == ob) return true ;
        if (obj == null) return false ;
        ExtraCredentials credentials = (ExtraCredentials) ob ;

        return  additionalCode.equals(credentials.getAdditionalCode()) && (encoder.matches(userPassword , credentials.getUserPassword()));
    }



}
