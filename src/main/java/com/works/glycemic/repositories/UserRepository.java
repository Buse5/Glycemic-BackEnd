package com.works.glycemic.repositories;

import com.works.glycemic.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmailEqualsIgnoreCase(String email);

}
