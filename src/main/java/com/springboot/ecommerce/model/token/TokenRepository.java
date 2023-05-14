package com.springboot.ecommerce.model.token;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    @Transactional
    @Modifying
    @Query("update Token as t " +
            "set t.confirmedRegister = true " +
            "where t.token = ?1")
    int updateConfirmedAt(String token);


    @Query("select t " +
            "from Token as t inner join User as u " +
            "on t.user.id = u.id " +
            "where t.user.id = ?1 and (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokenByUser(Long id);

}
