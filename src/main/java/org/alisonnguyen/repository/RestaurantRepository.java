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
                restaurant.setDistance(Double.parseDouble(line[2]));
                restaurant.setPrice(Double.parseDouble(line[3]));
                int cuisineId = Integer.parseInt(line[4]);

                // Fetch the corresponding Cuisine object
                Cuisine cuisine = dataLoader.getSession().get(Cuisine.class, cuisineId);
                restaurant.setCuisine(cuisineId);

                // Ensure the Cuisine has the Restaurant in its list
                List<Restaurant> restaurants = cuisine.getRestaurants();
                if (restaurants == null) {
                    restaurants = new ArrayList<>();
                    cuisine.setRestaurants(restaurants);
                }
                restaurants.add(restaurant);
            });

            // Close the CsvToDatabase instance
            dataLoader.close();
        }

}
