package com.example.clothing.DAO;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.clothing.Entities.OTP;

import jakarta.transaction.Transactional;

public interface OtpRepository extends CrudRepository<OTP, Integer> {

    @Query(value = "select * from otp where email=:Email order by created_at desc limit 1", nativeQuery = true)
    public OTP getlatestOTPByEmail(@Param("Email") String Email);

    @Transactional
    @Modifying
    public void deleteByExpiresATLessThan(LocalDateTime now);

}
