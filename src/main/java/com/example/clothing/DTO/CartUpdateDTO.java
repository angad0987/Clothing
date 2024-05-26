package com.example.clothing.DTO;

public class CartUpdateDTO {
    private String itemid;
    private int quantity;

    // Getters and Setters
    public String getItemid() {
        return itemid;
    }

    @Override
    public String toString() {
        return "CartUpdateDTO [itemid=" + itemid + ", quantity=" + quantity + "]";
    }

    public void setItemid(String itemId) {
        this.itemid = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
