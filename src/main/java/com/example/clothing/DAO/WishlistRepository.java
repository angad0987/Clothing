package com.example.clothing.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clothing.Entities.Wishlist;

import jakarta.transaction.Transactional;

@Repository
public interface WishlistRepository extends CrudRepository<Wishlist, Integer> {
        public boolean existsByItemidAndUserid(int item_id, String user_id);

        @Query(value = "select item_id from wishlist where user_id=:userId", nativeQuery = true)
        public List<Integer> getWishlistItemIds(@Param("userId") String user_id);

        @Query(value = "SELECT id,item_name,item_price,SUM(quantity),category,item_description,item_id,user_id FROM wishlist WHERE user_id=:userId  "
                        +
                        "GROUP BY id, item_name, item_price, category, item_description, item_id, user_id", nativeQuery = true)
        public List<Object[]> getWishlistItems(@Param("userId") String user_id);

        @Query(value = "SELECT * " +
                        "FROM (SELECT *, ROW_NUMBER() OVER (PARTITION BY item_type) AS row_no " +
                        "FROM clothing_items  " +
                        "WHERE item_type IN (SELECT DISTINCT category FROM wishlist WHERE user_id = :userId)) " +
                        "AS ranked_items " +
                        "WHERE row_no <= 3 && gender COLLATE utf8mb4_general_ci = :g", nativeQuery = true)
        List<Object[]> getWishlistItemTypes(@Param("userId") String userId, @Param("g") String gender);

        @Transactional
        @Modifying
        @Query(value = "delete from wishlist where user_id=:userId && item_id=:itemid", nativeQuery = true)
        public void deleteFromWishlist(@Param("userId") String customer_id, @Param("itemid") int item_id);

}
