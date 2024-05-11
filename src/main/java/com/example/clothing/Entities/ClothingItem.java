package com.example.clothing.Entities;

import jakarta.persistence.*;

/**
 *
 * @author Angad
 */
@Entity
@Table(name = "clothing_items")
public class ClothingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "item_name")
    private String name;
    @Column(name = "item_type")
    private String type;
    @Column(name = "item_material")
    private String material;
    @Column(name = "item_color")
    private String color;
    @Column(name = "weather_condition")
    private String condition;
    private String description;
    private float price;
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "ClothingItem [id=" + id + ", name=" + name + ", type=" + type + ", material=" + material + ", color="
                + color + ", condition=" + condition + ", description=" + description + ", price=" + price + ", gender="
                + gender + ", getGender()=" + getGender() + ", getPrice()=" + getPrice() + ", getDescription()="
                + getDescription() + ", getId()=" + getId() + ", getName()=" + getName() + ", getType()=" + getType()
                + ", getMaterial()=" + getMaterial() + ", getColor()=" + getColor() + ", getCondition()="
                + getCondition() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
                + super.toString() + "]";
    }

}
