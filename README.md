# Overview
This application is a Java application that is designed to locate local restaurants nearby. It provides functionality 
have the user input their preference for restaurant name, customer rating (1-5 stars), distance (1-10 miles), 
price ($10-50), and cuisine. Based on the criteria given it should return up to five best matches. If there are fewer 
than 5 matches all of them will be returned. If are are no matches the user will be altered there are no matches. 

## Technologies
- Java
- Hibernate
- Docker
- MySQL 
- Maven
- JUnit

# Project Structure
- *org.alisonnguyen.model*: Contains the entity classes representing database tables
- *org.alisonnguyen.repository*: Contains the repository interfaces for loading data into the database
- *org.alisonnguyen.services*: Contains the functionality for querying by criteria 
- App.java is the main driver for where the application will be run 

# Approaches & Decisions

## Repository 
1. Single Map with Composite Key
   RestaurantName: {customerRating, distance, price, cuisine}
    ### Advantages:
        - Simple structure
        - Easy implementation with need for frameworks
        - Low overhead becuase of simplicity 
    ### Caveat:
        - Brute force solution is inefficent for large data sets
        - Memory consumption for large datasets
        - Limited flexibility for updating and adding criteria

2. Use Hibernate Object Relational Mapping
   ### Advantages:
        - Data peristence does not need to be reloaded every run
        - CRUD operations availabile through ORM
        - Maintainable and scaleable for large data sets and as business requirements added 
   ### Caveat:
        - Complexity in set up and configuration
        - Performance overhead because of abstraction 
        - Comeplexity in strucutre makes debugging more complex 


# Setup 
1. Before running have Java and Maven installed
2. Clone this repo
3. Configure your database settings in application properties 
