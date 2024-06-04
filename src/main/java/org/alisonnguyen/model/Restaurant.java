package org.alisonnguyen.model;
import jakarta.persistence.*;

import java.util.List;


@NamedQueries({
        @NamedQuery(
                name = "matchByName",
                query = "SELECT r FROM Restaurant r WHERE LOWER(r.name) LIKE '%' || LOWER(:restaurantName) || '%'"
        ),
        @NamedQuery(
                name = "matchByRating",
                query = "SELECT r FROM Restaurant r WHERE r.customerRating >= :preferredRating ORDER BY r.customerRating ASC"
        ),
        @NamedQuery(
                name = "matchByDistance",
                query = "SELECT r FROM Restaurant r WHERE r.distance <= :distance ORDER BY r.distance ASC"
        )
})

@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int customerRating;
    private double distance;
    private double price;

    private int cuisineId;
    // Getters and setters
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

    public int getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(int customerRating) {
        this.customerRating = customerRating;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCuisine() {
        return cuisineId;
    }

    public void setCuisine(int cuisineId) {
        this.cuisineId = cuisineId;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return String.format("\n%-20s %-10d %-10.2f %-10.2f %-20s",
                name, customerRating, distance, price, cuisineId);
    }

    public void printRestaurants(List<Restaurant> restaurantList){
        String header = String.format("%-20s %-10s %-10s %-10s %-20s", "Restaurant", "Rating", "Distance", "Price", "Cuisine");
        System.out.println(header);
        for (Restaurant restaurant : restaurantList) {
            System.out.println(restaurant);
        }
    }
}