package org.perfumepedia.DataBase.repository;

import org.perfumepedia.DataBase.model.User;
import org.perfumepedia.DataBase.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    List<VerificationToken> findByUserEmail(String email);
    Optional<VerificationToken> findByToken(String token);
    void deleteByUser(User user);
    List<VerificationToken> findByUser(User user);
}
