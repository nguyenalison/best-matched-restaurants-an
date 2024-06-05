package org.alisonnguyen.repository;

import org.alisonnguyen.model.Cuisine;
import org.alisonnguyen.model.Restaurant;

public class CuisineRepository {
    DataLoader dataLoader = new DataLoader(Cuisine.class);
    public void populateCuisineData(String file){
        // Populate Cuisine data
        dataLoader.populateDatabase(file, Cuisine.class, (line, cuisine) -> {
            cuisine.setName(line[1]);
        });
        dataLoader.close();
    }
}
