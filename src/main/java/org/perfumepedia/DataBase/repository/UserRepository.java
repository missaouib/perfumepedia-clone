package org.perfumepedia.DataBase.repository;

import org.perfumepedia.DataBase.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByLogin(String login);
}
