package com.example.clothing.Entities;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author Angad
 */
@Entity
@Table(name = "order_table")
public class Order {

    public Order() {
    }

    @Id
    @Column(name = "order_id")
    private int order_id;

    @Column(name = "customer_id")
    private String customer_id;

    @Temporal(TemporalType.DATE) // Specify temporal type as DATE
    @Column(name = "order_date")
    private Date order_date;

    @Column(name = "shipping_address")
    private String shipping_address;

    @Column(name = "billing_address")
    private String billing_address;

    @Column(name = "total_amount")
    private float total_amount;

    @Column(name = "payment_method")
    private String payment_method;

    @Column(name = "order_status")
    private String order_status;

    @OneToMany(fetch = FetchType.EAGER)
    private List<ClothingItem> clothing_items;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Cart> cartItems;

    @Column(name = "tax_amount")
    private float tax;

    @Column(name = "promo_code")
    private String promo_code;

    public Order(List<ClothingItem> clothingItems, Map<String, String> customerDetails) {
        Random r = new Random();
        this.order_id = r.nextInt(99999) + 10000;
        this.customer_id = customerDetails.get("customer_id");
        this.order_date = new Date();
        this.shipping_address = customerDetails.get("shipping_address");
        this.billing_address = customerDetails.get("shipping_address");
        this.clothing_items = clothingItems;
        this.payment_method = customerDetails.get("payment_method");
        this.total_amount = Integer.parseInt(customerDetails.get("total_amount"));
        this.tax = Float.parseFloat(customerDetails.get("tax"));
        this.promo_code = customerDetails.get("promo_code");
        this.order_status = "placed";
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(String billing_address) {
        this.billing_address = billing_address;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public List<ClothingItem> getClothing_items() {
        return clothing_items;
    }

    public void setClothing_items(List<ClothingItem> clothing_items) {
        this.clothing_items = clothing_items;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public String getPromo_code() {
        return promo_code;
    }

    public void setPromo_code(String promo_code) {
        this.promo_code = promo_code;
    }

    @Override
    public String toString() {
        return "Order [order_id=" + order_id + ", customer_id=" + customer_id + ", order_date=" + order_date
                + ", shipping_address=" + shipping_address + ", billing_address=" + billing_address + ", total_amount="
                + total_amount + ", payment_method=" + payment_method + ", order_status=" + order_status
                + ", clothing_items=" + clothing_items + ", tax=" + tax + ", promo_code=" + promo_code + "]";
    }

    public List<Cart> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Cart> cartItems) {
        this.cartItems = cartItems;
    }

}
