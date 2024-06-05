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
- **org.alisonnguyen.model**: Contains the entity classes representing database tables
- **org.alisonnguyen.repository**: Contains the repository interfaces for loading data into the database
- **org.alisonnguyen.services**: Contains the functionality for querying by criteria
- **src/main/resources/data**: Location of CSV files
- App.java is the main driver for where the application will be run 


## Model
1. Restaurant
  - String Restaurant name
  - int customerRatings
  - int distance
  - int price
  - int cuisineId
2. Cuisine
  - int id
  - String Name 

# Repository 
### Approaches & Decisions
The following are viable solutions however come with their caveats. This application will take approach #2 for the reasons that efficiency, maintainability and scalability outwiegh simplicity of structure, learning curve for set up and the inefficiency. 

**DataLoader.java** used to read CSV files and populate the database. If data and database exist then it skips the CSV loading

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
     - Without a frontend interface Hibernate logs can be cluttered and difficult for users to interpret 

## Service
My original approach was to use NamedQueries for each type of criteria (matchByName, matchByRating, matchByDistance, etc.).
I have decided to use an TypedQuery Entity manager which is used to manage peristing the entities. This is a good decision for scalability, should 
there be CRUD implementation for peristing to the database. For this application Entity Manager is only used to query over entites. 

## App
1. When the application is run, Hibernate logging will display indicating database connection and dataloading
2. Application will asked user for input for criteria. If the input values are invalid the user will be asked to enter valid input
    - User can press enter to leave blank if they don't have a preference
3. All top results will display: Restaurant Name, customer rating, distance, price, and cuisine name

# Setup 
1. Before running have Java and Maven installed
2. Clone this repo
3. Configure your database settings in hibernate.cfg.xml
   - Modify the database connection settings (connection.url, connection.username, connection.password, etc.) as needed for your MySQL setup. 
5. mvn clean compile exec:java -Dexec.mainClass="org.alisonnguyen.App"
   

## Future Implementation and Next Steps
1. Extensive Unit testing
2. Prioritize Restaurant name who's input substring appreares earlier in the string (at the moment just if name contains substring) 
