package org.alisonnguyen;

import junit.framework.TestCase;
import org.alisonnguyen.model.Restaurant;
import org.alisonnguyen.repository.CuisineRepository;
import org.alisonnguyen.repository.RestaurantRepository;
import org.alisonnguyen.service.MatchingService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class MatchingServiceTest extends TestCase {
    private MatchingService matchingService;
    private static final String  RESTAURANT_CSV = "src/main/resources/data/restaurants.csv";
    private static final String  CUISINE_CSV = "src/main/resources/data/cuisines.csv";
    @BeforeEach
    public void setUp() {

        matchingService = new MatchingService();

        CuisineRepository cuisineRepo = new CuisineRepository();
        cuisineRepo.populateCuisineData(CUISINE_CSV);

        RestaurantRepository restaurantRepo  = new RestaurantRepository();
        restaurantRepo.populateRestaurantData(RESTAURANT_CSV);
    }

    @Test
    public void testMatchByRatingDistancePrice() {
        List<Restaurant> results = matchingService.matchByCriteria("", 4, 1, 10, null);
        List<String> expectedNames = Arrays.asList("Deliciousgenix", "McDonalds");
        List<String> resultNames = results.stream().map(Restaurant::getName).collect(Collectors.toList());
        assertEquals(expectedNames, resultNames);
    }

    @Test
    public void testMatchByRatingDistancePriceUpperBound() {
        List<Restaurant> results = matchingService.matchByCriteria("", 5, 10, 50, null);
        List<String> expectedNames = Arrays.asList("Crisp Delicious", "Gusto Delicious", "Local Delicious", "Deliciousbea", "Deliciousio");
        List<String> resultNames = results.stream().map(Restaurant::getName).collect(Collectors.toList());
        assertEquals(expectedNames, resultNames);
    }

    @Test
    public void testMatchByRatingDistancePriceLowerBound() {
        List<Restaurant> results = matchingService.matchByCriteria("", 1, 10, 10, null);
        List<String> expectedNames = Arrays.asList("Deliciousgenix", "Deliciousquipo", "Hut Chow", "Hideout Chow", "Chowish");
        List<String> resultNames = results.stream().map(Restaurant::getName).collect(Collectors.toList());
        assertEquals(expectedNames, resultNames);
    }

    @Test
    public void testInvalidInput() {
        List<Restaurant> results = matchingService.matchByCriteria("", -1, -10, -10, null);
        List<String> expectedNames = Arrays.asList();
        List<String> resultNames = results.stream().map(Restaurant::getName).collect(Collectors.toList());
        assertEquals(expectedNames, resultNames);
    }

}
