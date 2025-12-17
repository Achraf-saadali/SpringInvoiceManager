package org.example.springmanager2.Controller;





import org.example.springmanager2.CredentialsSchema.*;
import org.example.springmanager2.Entity.Admin;
import org.example.springmanager2.Entity.Client;
import org.example.springmanager2.Entity.Comptable;
import org.example.springmanager2.Entity.Enums.ROLES;
import org.example.springmanager2.Service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Custom AuthenticationProvider for multiple credentials
 */
@Component
public class MultiCredentialAuthenticationProvider implements AuthenticationProvider {


    private final RolesRouter rolesRouter;

    public MultiCredentialAuthenticationProvider(RolesRouter rolesRouter) {
    this.rolesRouter = rolesRouter;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
          return switch(authentication.getCredentials()){
              case ExtraCredentials e -> rolesRouter.authenticate(e.getUserRole(),authentication );

             case Admin c -> rolesRouter.authenticate(ROLES.ADMIN,authentication );
             default->
                rolesRouter.authenticate(ROLES.ADMIN,authentication );

         };


    }

    @Override
    public boolean supports(Class<?> authentication) {

        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
