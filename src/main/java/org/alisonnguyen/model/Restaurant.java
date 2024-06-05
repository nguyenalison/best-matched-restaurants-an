package org.alisonnguyen.model;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int customerRating;
    private int distance;
    private int price;
    @Column(insertable = false, updatable = false)
    private int cuisineId;

    @ManyToOne
    @JoinColumn(name = "cuisineId")
    private Cuisine cuisine;

    public Restaurant(){}
    public Restaurant(String name, int customerRating, int distance, int price, int cuisineId) {
        this.name = name;
        this.customerRating = customerRating;
        this.distance = distance;
        this.price = price;
        this.cuisineId = cuisineId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(int customerRating) {
        this.customerRating = customerRating;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(int cuisineId) {
        this.cuisineId = cuisineId;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return String.format("\n%-20s %-10d %-10d %-10d %-20s",
                name, customerRating, distance, price, cuisine.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant)) return false;
        Restaurant that = (Restaurant) o;
        return getCustomerRating() == that.getCustomerRating() && getDistance() == that.getDistance() && getPrice() == that.getPrice() && getCuisineId() == that.getCuisineId() && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCustomerRating(), getDistance(), getPrice(), getCuisineId());
    }

}