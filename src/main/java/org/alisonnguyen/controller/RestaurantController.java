package org.alisonnguyen.controller;

import org.alisonnguyen.model.Cuisine;
import org.alisonnguyen.model.Restaurant;
import org.alisonnguyen.repository.DataLoader;
import org.alisonnguyen.service.MatchingService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class RestaurantController {
    public static void main(String[] args)  {
        DataLoader dataLoader = new DataLoader(Restaurant.class, Cuisine.class);

        // Populate Cuisine data
        dataLoader.populateDatabase("src/main/resources/data/cuisines.csv", Cuisine.class, (line, cuisine) -> {
            cuisine.setName(line[1]);
        });

        // Populate Restaurant data
        dataLoader.populateDatabase("src/main/resources/data/restaurants.csv", Restaurant.class, (line, restaurant) -> {
            restaurant.setName(line[0]);
            restaurant.setCustomerRating(Integer.parseInt(line[1]));
            restaurant.setDistance(Double.parseDouble(line[2]));
            restaurant.setPrice(Double.parseDouble(line[3]));
            int cuisineId = Integer.parseInt(line[4]);

            // Fetch the corresponding Cuisine object
            Cuisine cuisine = dataLoader.getSession().get(Cuisine.class, cuisineId);
            restaurant.setCuisine(cuisineId);

            // Fetch the corresponding Cuisine object
            Cuisine c = dataLoader.getSession().get(Cuisine.class, cuisineId);
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

        MatchingService service = new MatchingService();
        System.out.println(service.matchByRating(3));
        
    }
}

