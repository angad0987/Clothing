package com.example.clothing.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clothing.Entities.ClothingItem;

@Repository
public interface ClothingItemRepository extends CrudRepository<ClothingItem, Integer> {

        @Query(value = "SELECT DISTINCT item_type FROM clothing_items WHERE :condition IN (weather_condition) AND gender = :gender "
                        + "OR :condition IN (SUBSTRING_INDEX((SUBSTRING_INDEX(weather_condition,',',1)),',',-1)) AND gender = :gender "
                        + "OR :condition IN (SUBSTRING_INDEX((SUBSTRING_INDEX(weather_condition,',',2)),',',-1)) AND gender = :gender "
                        + "OR :condition IN (SUBSTRING_INDEX((SUBSTRING_INDEX(weather_condition,',',3)),',',-1)) AND gender = :gender "
                        + "OR :condition IN (SUBSTRING_INDEX((SUBSTRING_INDEX(weather_condition,',',4)),',',-1)) AND gender = :gender "
                        + "OR :condition IN (SUBSTRING_INDEX((SUBSTRING_INDEX(weather_condition,',',5)),',',-1)) AND gender = :gender "
                        + "OR :condition IN (SUBSTRING_INDEX((SUBSTRING_INDEX(weather_condition,',',6)),',',-1)) AND gender = :gender "
                        + "OR :condition IN (SUBSTRING_INDEX((SUBSTRING_INDEX(weather_condition,',',7)),',',-1)) AND gender = :gender "
                        + "OR :condition IN (SUBSTRING_INDEX((SUBSTRING_INDEX(weather_condition,',',8)),',',-1)) AND gender = :gender "
                        + "OR :condition IN (SUBSTRING_INDEX((SUBSTRING_INDEX(weather_condition,',',9)),',',-1)) AND gender = :gender "
                        + "OR :condition IN (SUBSTRING_INDEX((SUBSTRING_INDEX(weather_condition,',',10)),',',-1)) AND gender = :gender", nativeQuery = true)
        public List<String> getTypes(@Param("condition") String condition, @Param("gender") String gender);

        public List<ClothingItem> findByTypeAndGender(String type, String gender);

        public List<ClothingItem> findByTypeAndGenderAndPriceBetween(String type, String gender, Integer min,
                        Integer max);

        @Query(value = "SELECT *" +
                        " FROM clothing_items " +
                        " WHERE item_type=:type " +
                        " AND gender=:gender " +
                        " AND id IN ( " +
                        " SELECT clothingItem_id " +
                        " FROM discount " +
                        " WHERE start_date <= CURRENT_DATE  " +
                        " AND end_date >= CURRENT_DATE " +
                        " AND discount_percentage >=:dis )", nativeQuery = true)
        public List<ClothingItem> getByTypeAndGenderAndDiscount(@Param("type") String type,
                        @Param("gender") String gender, @Param("dis") Float dis);

        @Query(value = "SELECT *" +
                        " FROM clothing_items " +
                        " WHERE item_type =:type " +
                        " AND gender=:gender " +
                        " AND item_color =:color" +
                        " AND id IN ( " +
                        " SELECT clothingItem_id " +
                        " FROM discount " +
                        " WHERE start_date <= CURRENT_DATE  " +
                        " AND end_date >= CURRENT_DATE " +
                        " AND discount_percentage >=:dis )", nativeQuery = true)
        public List<ClothingItem> getByTypeAndGenderAndDiscountAndColor(@Param("type") String type,
                        @Param("gender") String gender, @Param("dis") Float dis, @Param("color") String color);

        @Query(value = "SELECT *" +
                        " FROM clothing_items " +
                        " WHERE item_type=:type " +
                        " AND gender=:gender " +
                        " AND price>=:minprice" +
                        " AND price<=:maxprice" +
                        " AND id IN ( " +
                        " SELECT clothingItem_id " +
                        " FROM discount " +
                        " WHERE start_date <= CURRENT_DATE  " +
                        " AND end_date >= CURRENT_DATE " +
                        " AND discount_percentage >=:dis )", nativeQuery = true)
        public List<ClothingItem> getByTypeAndGenderAndDiscountAndPriceRange(@Param("type") String type,
                        @Param("gender") String gender, @Param("dis") Float dis,
                        @Param("minprice") Integer minPrice, @Param("maxprice") Integer maxPrice);

        @Query(value = "SELECT *" +
                        " FROM clothing_items " +
                        " WHERE item_type=:type " +
                        " AND gender=:gender " +
                        " AND price>=:minprice" +
                        " AND price<=:maxprice" +
                        " AND item_color=:color " +
                        " AND id IN ( " +
                        " SELECT clothingItem_id " +
                        " FROM discount " +
                        " WHERE start_date <= CURRENT_DATE  " +
                        " AND end_date >= CURRENT_DATE " +
                        " AND discount_percentage >=:dis )", nativeQuery = true)
        public List<ClothingItem> getByTypeAndGenderAndDiscountAndPriceRangeAndColor(@Param("type") String type,
                        @Param("gender") String gender, @Param("dis") Float dis,
                        @Param("minprice") Integer minPrice, @Param("maxprice") Integer maxPrice,
                        @Param("color") String color);

        public List<ClothingItem> findByTypeAndGenderAndColor(String type, String gender, String color);

        public List<ClothingItem> findByTypeAndGenderAndColorAndPriceBetween(String type, String gender, String color,
                        Integer min, Integer max);

        public List<ClothingItem> findByType(String type);

        @Query(value = "SELECT DISTINCT CASE " +
                        "WHEN SUBSTRING_INDEX(weather_condition, ',', 1) LIKE CONCAT(:condition ,'%') THEN SUBSTRING_INDEX(weather_condition, ',', 1) "
                        +
                        "WHEN SUBSTRING_INDEX(weather_condition, ',', 2) LIKE CONCAT(:condition ,'%') THEN SUBSTRING_INDEX(weather_condition, ',', 2) "
                        +
                        "WHEN SUBSTRING_INDEX(weather_condition, ',', 3) LIKE CONCAT(:condition ,'%') THEN SUBSTRING_INDEX(weather_condition, ',', 3) "
                        +
                        "WHEN SUBSTRING_INDEX(weather_condition, ',', 4) LIKE CONCAT(:condition ,'%') THEN SUBSTRING_INDEX(weather_condition, ',', 4) "
                        +
                        "WHEN SUBSTRING_INDEX(weather_condition, ',', 5) LIKE CONCAT(:condition ,'%') THEN SUBSTRING_INDEX(weather_condition, ',', 5) "
                        +
                        "END AS weather_condition " +
                        "FROM clothing_items " +
                        "WHERE " +
                        "SUBSTRING_INDEX(weather_condition, ',', 1) LIKE CONCAT(:condition ,'%') OR " +
                        "SUBSTRING_INDEX(weather_condition, ',', 2) LIKE CONCAT(:condition ,'%') OR " +
                        "SUBSTRING_INDEX(weather_condition, ',', 3) LIKE CONCAT(:condition ,'%') OR " +
                        "SUBSTRING_INDEX(weather_condition, ',', 4) LIKE CONCAT(:condition ,'%') OR " +
                        "SUBSTRING_INDEX(weather_condition, ',', 5) LIKE CONCAT(:condition ,'%')", nativeQuery = true)
        public List<String> getCollectionTypes(@Param("condition") String condition);

        public ClothingItem findById(int id);

}
