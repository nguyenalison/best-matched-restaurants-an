package org.alisonnguyen.repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.BiConsumer;

public class DataLoader {
    private SessionFactory factory;
    private Session session;

    public DataLoader(Class<?>... annotatedClasses) {
        Configuration config = new Configuration().configure("hibernate.cfg.xml");
        for (Class<?> annotatedClass : annotatedClasses) {
            config.addAnnotatedClass(annotatedClass);
        }
        factory = config.buildSessionFactory();
    }

    public <T> void populateDatabase(String csvFilePath, Class<T> entityClass, BiConsumer<String[], T> entityMapper) {
        session = factory.openSession();

        try {
            session.beginTransaction();

            // Check if data already exists
            List<T> entities = session.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
            if (entities.isEmpty()) {
                // Read CSV file
                CSVReader reader = new CSVReader(new FileReader(csvFilePath));
                String[] line;

                // Skip header
                reader.readNext();

                // Iterate through each line in the CSV
                while ((line = reader.readNext()) != null) {
                    // Create entity object
                    Constructor<T> constructor = entityClass.getConstructor();
                    T entity = constructor.newInstance();

                    // Map CSV data to entity
                    entityMapper.accept(line, entity);

                    // Save entity object to database
                    session.save(entity);
                }

                reader.close();
                System.out.println(entityClass.getSimpleName() + " data populated successfully!");
            } else {
                System.out.println(entityClass.getSimpleName() + " data already populated, skipping CSV loading.");
            }

            // Commit transaction
            session.getTransaction().commit();
        } catch (IOException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } finally {
            // Close the session
            session.close();
        }
    }

    public Session getSession() {
        if (session == null || !session.isOpen()) {
            session = factory.openSession();
        }
        return session;
    }
    public void close() {
        // Close the factory
        if (factory != null) {
            factory.close();
        }
    }
}