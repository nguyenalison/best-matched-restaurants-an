package org.alisonnguyen.service;
import jakarta.persistence.*;
import org.alisonnguyen.model.Restaurant;
import java.util.Comparator;
import java.util.List;

public class MatchingService {
    public  List<Restaurant> matchByCriteria(String name, Integer rating, Integer distance, Integer price, String cuisine) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        String jpql = "SELECT r FROM Restaurant r";

        if (cuisine != null && !cuisine.isEmpty()) {
            jpql += " JOIN Cuisine c ON r.cuisineId= c.id WHERE LOWER(c.name) LIKE LOWER(:cuisine)";
        } else {
            jpql += " WHERE 1=1";
        }

        if (name != null && !name.isEmpty()) {
            jpql += " AND LOWER(r.name) LIKE :name";
        }
        if (rating != null && rating != 0) {
            jpql += " AND r.customerRating >= :rating";
        }
        if (distance != null && distance != 0) {
            jpql += " AND r.distance <= :distance";
        }
        if (price != null && price != 0) {
            jpql += " AND r.price <= :price";
        }

        System.out.println(jpql);
        TypedQuery<Restaurant> query = entityManager.createQuery(jpql, Restaurant.class);

        if (cuisine != null && !cuisine.isEmpty()) {
            query.setParameter("cuisine", "%" + cuisine.toLowerCase() + "%");
        }
        if (name != null && !name.isEmpty()) {
            query.setParameter("name", "%" + name.toLowerCase() + "%");
        }
        if (rating != null && rating != 0) {
            query.setParameter("rating", rating);
        }
        if (distance != null && distance != 0) {
            query.setParameter("distance", distance);
        }
        if (price != null && price != 0) {
            query.setParameter("price", price);
        }

        // Set maximum results to 5
        query.setMaxResults(5);

        List<Restaurant> restaurants = query.getResultList();
        entityManager.close();

        // Sort the list based on the rules provided
        restaurants.sort(Comparator.comparing(Restaurant::getDistance)
                .thenComparing(Comparator.comparing(Restaurant::getCustomerRating).reversed())
                .thenComparing(Restaurant::getPrice)
                .thenComparing(Restaurant::getName));


        return restaurants;
    }
}
