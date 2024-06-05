package org.alisonnguyen.repository;

import org.alisonnguyen.model.Cuisine;
import org.alisonnguyen.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository {
    DataLoader dataLoader = new DataLoader(Restaurant.class);
    public void populateRestaurantData(String file) {
        // Populate Restaurant data
        dataLoader.populateDatabase(file, Restaurant.class, (line, restaurant) -> {
            restaurant.setName(line[0]);
            restaurant.setCustomerRating(Integer.parseInt(line[1]));
            restaurant.setDistance(Integer.parseInt(line[2]));
            restaurant.setPrice(Integer.parseInt(line[3]));
            int cuisineId = Integer.parseInt(line[4]);

            Cuisine cuisine = dataLoader.getSession().get(Cuisine.class, cuisineId);
            if (cuisine == null) {
                // Log an error and skip adding the restaurant
                System.err.println("Cuisine with ID " + cuisineId + " not found for restaurant " + restaurant.getName());
//                return;
            }
            // Ensure the Cuisine has the Restaurant in its list
            List<Restaurant> restaurants = cuisine.getRestaurants();
            if (restaurants == null) {
                restaurants = new ArrayList<>();
                cuisine.setRestaurants(restaurants);
            }
            restaurants.add(restaurant);
            restaurant.setCuisine(cuisine);

        });

        // Close the CsvToDatabase instance
        dataLoader.close();
    }

}