package org.alisonnguyen.service;

import jakarta.persistence.TypedQuery;
import org.alisonnguyen.controller.RestaurantController;
import org.alisonnguyen.model.Restaurant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
}
