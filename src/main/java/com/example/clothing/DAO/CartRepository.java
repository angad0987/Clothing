package com.example.clothing.DAO;

import java.util.List;
import java.util.Optional;

import org.eclipse.angus.mail.handlers.text_html;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clothing.Entities.Cart;

import jakarta.transaction.Transactional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {

    @Query(value = "SELECT clothing_items.id,item_color,item_name,clothing_items.price,SUM(cart.quantity),cart.id AS 'Total Quantity',cart.item_id FROM cart INNER JOIN clothing_items ON clothing_items.id=cart.item_id WHERE customer_id=:customerid GROUP BY item_id", nativeQuery = true)
    public List<Object[]> getCartItems(@Param("customerid") String customer_id);

    @Transactional
    public void deleteByCustomeridAndItemid(String customer_id, int item_id);

    @Transactional
    public void deleteByCustomerid(String customerid);

    public Cart findByItemid(int int1);

    @Query(value = "select * from cart where customer_id=:customerid && item_id=:itemid", nativeQuery = true)
    public Cart getByCustomerIdAndItemid(@Param("customerid") String customerid, @Param("itemid") int quan);

    @Query(value = "select item_id from cart where customer_id=:customerid",nativeQuery = true))
    public List<Integer> getAllClothingItemsIdsFromCart(@Param("customerid") String customerId);

    @Query(value = "select item_id,quantity from cart where customer_id=:customerid", nativeQuery = true)
    public List<Object[]> getItemIdAndQuantity(@Param("customerid") String customerId);

}
