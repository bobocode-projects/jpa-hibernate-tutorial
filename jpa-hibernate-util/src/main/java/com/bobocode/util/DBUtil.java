package com.bobocode.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class DBUtil {
    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("bobocode");
            // you can also use properties map instead of persistence.xml
            // emf = Persistence.createEntityManagerFactory("bobocode", getPropertiesMap());
        }
        return emf;
    }

    public static void destroy() {
        emf.close();
    }


    // this method can be used to provide settings instead of persistence.xml
    private static Map getPropertiesMap() {
        Map<String, String> properties = new HashMap<>();

        properties.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/bobocode_db");
        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.put("hibernate.connection.username", "bobouser");
        properties.put("hibernate.connection.password", "bobopass");
        properties.put("hibernate.connection.pool_size", "10");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        properties.put("hibernate.hbm2ddl.auto", "create-drop");

        return properties;
    }
}
