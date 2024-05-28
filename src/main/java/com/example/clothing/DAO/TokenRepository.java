package com.example.clothing.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.clothing.Entities.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Integer> {

    @Query(value = "select * from tokens  t INNER JOIN users  u ON u.user_id=t.userid where t.userid =:userid && t.is_logged_out=false", nativeQuery = true)
    List<Token> findBy_userid(String userid);

    Optional<Token> findByToken(String tokenName);

}
