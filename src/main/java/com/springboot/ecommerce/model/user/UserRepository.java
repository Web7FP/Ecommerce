package com.springboot.ecommerce.model.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Query("select u " +
            "from User as u " +
            "where u.email = ?1")
    User findByUsername(String email);


    @Transactional
    @Modifying
    @Query("update User as u " +
            "set u.enabled = true " +
            "where u.email = ?1")
    int enableUser(String email);
}
