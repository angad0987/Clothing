package com.example.clothing.Entities;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name = "item_id")
    private int itemid;
    @Column(name = "item_name")
    private String itemname;
    @Column(name = "item_price")
    private float itemprice;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "category")
    private String category;
    @Column(name = "item_description")
    private String description;
    @Column(name = "user_id")
    private String userid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public float getItemprice() {
        return itemprice;
    }

    public void setItemprice(float itemprice) {
        this.itemprice = itemprice;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {

        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getItem_id() {
        return itemid;
    }

    public void setItem_id(int item_id) {
        this.itemid = item_id;
    }

    public String getUser_id() {
        return userid;
    }

    public void setUser_id(String user_id) {
        this.userid = user_id;
    }

    public Wishlist(String itemname, float itemprice, int itemid, Long quantity, String category, String description) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.quantity = quantity;
        this.category = category;
        this.description = description;
    }

    public Wishlist() {
        // TODO Auto-generated constructor stub
    }

}
