package org.alisonnguyen;

import org.alisonnguyen.model.Restaurant;
import org.alisonnguyen.repository.CuisineRepository;
import org.alisonnguyen.repository.RestaurantRepository;
import org.alisonnguyen.service.MatchingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        final String  RESTAURANT_CSV = "src/main/resources/data/restaurants.csv";
        final String  CUISINE_CSV = "src/main/resources/data/cuisines.csv";

        MatchingService service = new MatchingService();
        List<Restaurant> matches = new ArrayList<>();

        RestaurantRepository restaurantRepo  = new RestaurantRepository();
        restaurantRepo.populateRestaurantData(RESTAURANT_CSV);

        CuisineRepository cuisineRepo = new CuisineRepository();
        cuisineRepo.populateCuisineData(CUISINE_CSV);

        Scanner scanner = new Scanner(System.in);
        String userInputRestaurant, userInputCuisine;
        int userInputRating, userInputDistance, userInputPrice;
        System.out.println("Enter the name of the restaurant (Leave blank if none): ");
        userInputRestaurant = scanner.nextLine();

        userInputRating = validNumericInput(scanner, "rating", Integer::parseInt, 0);

        userInputDistance = validNumericInput(scanner, "distance", Integer::parseInt, 0);

        userInputPrice = validNumericInput(scanner, "max budget", Integer::parseInt, 0);

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

    private static <T extends Number> T validNumericInput(Scanner scan, String prompt,
                                                          java.util.function.Function<String, T> parser, T defaultValue) {
        T input = null;
        while (input == null || input.doubleValue() < 0) {
            System.out.println("Enter your preferred " + prompt + " (Leave blank if none): ");
            String userInput = scan.nextLine();
            if (userInput.isEmpty()) {
                return defaultValue;
            }
            try {
                input = parser.apply(userInput);
                if (input.doubleValue() < 0) {
                    System.out.println("Value cannot be negative. Please enter a non-negative value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return input;
    }
}
