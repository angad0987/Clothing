package com.example.clothing.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.clothing.Entities.PromoCode;

@Repository
public interface PromoCodeRepository extends CrudRepository<PromoCode, Integer> {

}
