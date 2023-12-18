package com.borrowboss.authservice.authservice.Repository;

import com.borrowboss.authservice.authservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String username);
}
