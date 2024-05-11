package com.example.clothing.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clothing.Entities.Cart;

import jakarta.transaction.Transactional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {

    @Query(value = "SELECT clothing_items.id,item_color,item_name,clothing_items.price,SUM(cart.quantity),cart.id AS 'Total Quantity' FROM cart INNER JOIN clothing_items ON clothing_items.id=cart.item_id WHERE customer_id=:customerid GROUP BY item_id", nativeQuery = true)
    public List<Object[]> getCartItems(@Param("customerid") String customer_id);

    @Transactional
    public void deleteByCustomeridAndItemid(String customer_id, int item_id);

    @Transactional
    public void deleteByCustomerid(String customerid);

}
