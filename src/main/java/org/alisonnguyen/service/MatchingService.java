package org.alisonnguyen.service;

import jakarta.persistence.*;
import org.alisonnguyen.model.Restaurant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Comparator;
import java.util.List;

public class MatchingService {

    public List<Restaurant> matchByName(String input) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();

        TypedQuery<Restaurant> query = session.createNamedQuery("matchByName", Restaurant.class);
        query.setParameter("restaurantName", input);
        List<Restaurant> restaurants = query.getResultList();
        factory.close();
        session.close();
        return restaurants;
    }

    public List<Restaurant> matchByRating(int preferredRating){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();

        TypedQuery<Restaurant> query = session.createNamedQuery("matchByRating", Restaurant.class);
        query.setParameter("preferredRating", preferredRating);
        List<Restaurant> restaurants = query.getResultList();
        factory.close();
        session.close();
        return restaurants;
    }

    public List<Restaurant> matchByDistance(int distance){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();

        TypedQuery<Restaurant> query = session.createNamedQuery("matchByDistance", Restaurant.class);
        query.setParameter("distance", distance);
        List<Restaurant> restaurants = query.getResultList();
        factory.close();
        session.close();
        return restaurants;
    }

    public List<Restaurant> matchByBudget(int price){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();

        TypedQuery<Restaurant> query = session.createNamedQuery("matchByBudget", Restaurant.class);
        query.setParameter("price", price);
        List<Restaurant> restaurants = query.getResultList();
        factory.close();
        session.close();
        return restaurants;
    }

    public List<Restaurant> matchByCuisine(String cuisine){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();

        TypedQuery<Restaurant> query = session.createNamedQuery("matchByCuisine", Restaurant.class);
        query.setParameter("cuisine", cuisine);
        List<Restaurant> restaurants = query.getResultList();
        factory.close();
        session.close();
        return restaurants;
    }
    public  List<Restaurant> matchByCriteria(String name, Integer rating, Integer distance, Integer price, String cuisine) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        String jpql = "SELECT r FROM Restaurant r WHERE 1=1";

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
        if (cuisine != null && !cuisine.isEmpty()) {
            jpql += " AND LOWER(r.cuisine.name) LIKE :cuisine";
        }

        TypedQuery<Restaurant> query = entityManager.createQuery(jpql, Restaurant.class);

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
        if (cuisine != null && !cuisine.isEmpty()) {
            query.setParameter("cuisine", "%" + cuisine.toLowerCase() + "%");
        }

        List<Restaurant> restaurants = query.getResultList();
        entityManager.close();

        // Sort the list based on the rules provided
        restaurants.sort(Comparator.comparing(Restaurant::getDistance)
                .thenComparing(Comparator.comparing(Restaurant::getCustomerRating).reversed())
                .thenComparing(Restaurant::getPrice));

        return restaurants;
    }
}
