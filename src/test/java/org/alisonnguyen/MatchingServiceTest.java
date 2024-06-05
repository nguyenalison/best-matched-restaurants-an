package org.alisonnguyen;

import junit.framework.TestCase;
import org.alisonnguyen.model.Restaurant;
import org.alisonnguyen.service.MatchingService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;


public class MatchingServiceTest extends TestCase {
    private MatchingService matchingService;

    @BeforeEach
    public void setUp() {
        matchingService = new MatchingService();
        // Optionally, populate the database with test data here
    }

    @Test
    public void testMatchByCriteriaWithName() {
        List<Restaurant> results = matchingService.matchByCriteria("mcdonald", null, null, null, null);
        assertNotNull(results);
        assertTrue(results.size() <= 5);
        assertTrue(results.stream().allMatch(r -> r.getName().toLowerCase().contains("mcdonald")));
    }

    @Test
    public void testMatchByCriteriaWithRating() {
        List<Restaurant> results = matchingService.matchByCriteria(null, 3, null, null, null);
        assertNotNull(results);
        assertTrue(results.size() <= 5);
        assertTrue(results.stream().allMatch(r -> r.getCustomerRating() >= 3));
    }

    @Test
    public void testMatchByCriteriaWithDistance() {
        List<Restaurant> results = matchingService.matchByCriteria(null, null, 5, null, null);
        assertNotNull(results);
        assertTrue(results.size() <= 5);
        assertTrue(results.stream().allMatch(r -> r.getDistance() <= 5));
    }

    @Test
    public void testMatchByCriteriaWithPrice() {
        List<Restaurant> results = matchingService.matchByCriteria(null, null, null, 15, null);
        assertNotNull(results);
        assertTrue(results.size() <= 5);
        assertTrue(results.stream().allMatch(r -> r.getPrice() <= 15));
    }


    public void testSortOrder() {
        List<Restaurant> results = matchingService.matchByCriteria(null, null, null, null, "test");
        assertNotNull(results);
        assertTrue(results.size() <= 5);
        // Ensure results are sorted by distance, then rating, then price, then name
        for (int i = 1; i < results.size(); i++) {
            Restaurant prev = results.get(i - 1);
            Restaurant current = results.get(i);
            assertTrue(prev.getDistance() <= current.getDistance() ||
                    (prev.getDistance() == current.getDistance() && prev.getCustomerRating() >= current.getCustomerRating()) ||
                    (prev.getDistance() == current.getDistance() && prev.getCustomerRating() == current.getCustomerRating() && prev.getPrice() <= current.getPrice()) ||
                    (prev.getDistance() == current.getDistance() && prev.getCustomerRating() == current.getCustomerRating() && prev.getPrice() == current.getPrice() && prev.getName().compareTo(current.getName()) <= 0));
        }
    }

    @Test
    public void testMatchByCriteriaNoMatches() {
        List<Restaurant> results = matchingService.matchByCriteria("nonexistent", null, null, null, null);
        assertNotNull(results);
        assertEquals(0, results.size());
    }
}
