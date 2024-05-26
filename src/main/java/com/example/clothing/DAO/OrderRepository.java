package com.example.clothing.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clothing.Entities.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    @Query(value = "select * from order_table where customer_id=:customerid", nativeQuery = true)
    List<Order> findByCustomerid(@Param("customerid") String customer_id);

}
