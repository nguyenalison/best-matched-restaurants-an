package org.alisonnguyen;

import org.alisonnguyen.model.Restaurant;
import org.alisonnguyen.repository.CuisineRepository;
import org.alisonnguyen.repository.RestaurantRepository;
import org.alisonnguyen.service.MatchingService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;
    private static final int MIN_DISTANCE = 1;
    private static final int MAX_DISTANCE = 10;
    private static final int MIN_PRICE = 5;
    private static final int MAX_PRICE = 50;
    private static final String  RESTAURANT_CSV = "src/main/resources/data/restaurants.csv";
    private static final String  CUISINE_CSV = "src/main/resources/data/cuisines.csv";
    public static void main(String[] args) {
        MatchingService service = new MatchingService();
        List<Restaurant> matches = new ArrayList<>();

        CuisineRepository cuisineRepo = new CuisineRepository();
        cuisineRepo.populateCuisineData(CUISINE_CSV);

        RestaurantRepository restaurantRepo  = new RestaurantRepository();
        restaurantRepo.populateRestaurantData(RESTAURANT_CSV);



        Scanner scanner = new Scanner(System.in);
        String userInputRestaurant, userInputCuisine;
        int userInputRating, userInputDistance, userInputPrice;

        System.out.println("\n\n---------WELCOME TO ALI'S YELP---------------------------------");
        System.out.println("Enter the name of the restaurant (Leave blank if none): ");
        userInputRestaurant = scanner.nextLine();

        userInputRating = validNumericInput(scanner, "rating", Integer::parseInt, 0, MIN_RATING, MAX_RATING);

        userInputDistance = validNumericInput(scanner, "distance", Integer::parseInt, 0, MIN_DISTANCE, MAX_DISTANCE);

        userInputPrice = validNumericInput(scanner, "max budget", Integer::parseInt, 0, MIN_PRICE, MAX_PRICE);

        System.out.println("Enter the preferred cuisine (Leave blank if none): ");
        userInputCuisine = scanner.nextLine();

        String header = String.format("%-20s %-10s %-10s %-10s %-20s", "Restaurant", "Rating", "Distance", "Price", "Cuisine");
        matches = (service.matchByCriteria(userInputRestaurant,
                userInputRating,
                userInputDistance,
                userInputPrice,
                userInputCuisine));
        System.out.println(header + matches);
    }

    private static <T extends Number> T validNumericInput(Scanner scan, String prompt, java.util.function.Function<String, T> parser, T defaultValue, int minValue, int maxValue) {
        T input = null;
        while (input == null || input.doubleValue() < minValue || input.doubleValue() > maxValue) {
            System.out.println("Enter your preferred " + prompt + " (" + minValue + "-" + maxValue + ", Leave blank if none): ");
            String userInput = scan.nextLine();
            if (userInput.isEmpty()) {
                return defaultValue;
            }
            try {
                input = parser.apply(userInput);
                if (input.doubleValue() < minValue || input.doubleValue() > maxValue) {
                    System.out.println("Value must be between " + minValue + " and " + maxValue + ". Please enter a valid value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return input;
    }
}
