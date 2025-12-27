package org.example.springmanager2.Repository;

import org.example.springmanager2.Entity.GmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GmailTokenRepo extends JpaRepository<GmailToken, Long> {
    Optional<GmailToken> findByEmail(String email);
    boolean existsByEmail(String email);
}