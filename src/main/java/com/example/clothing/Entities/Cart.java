package com.example.clothing.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "customer_id")
    private String customerid;

    @Column(name = "item_id")
    private int itemid;

    @Column(name = "quantity")
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer_id() {
        return customerid;
    }

    public void setCustomer_id(String customer_id) {
        this.customerid = customer_id;
    }

    public int getItem_id() {
        return itemid;
    }

    public void setItem_id(int item_id) {
        this.itemid = item_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart() {
    }

    @Override
    public String toString() {
        return "Cart{" + "id=" + id + ", customer_id=" + customerid + ", item_id=" + itemid + ", quantity=" + quantity
                + '}';
    }

}
