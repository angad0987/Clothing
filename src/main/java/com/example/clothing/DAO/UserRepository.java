package com.example.clothing.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clothing.Entities.User;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    @Query("SELECT u.userId FROM User u WHERE u.userId = :userId AND u.password = :password")
    public String checkLogin(@Param("userId") String userId, @Param("password") String password);

    public User findByUserId(String userId);

    @Query(value = "select * from users where user_id=:username", nativeQuery = true)
    public User getUsername(@Param("username") String username);

}
