package com.bobocode.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class DBUtil {
    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("bobocode", getPropertiesMap());
        }
        return emf;
    }

    public static void destroy() {
        emf.close();
    }


    private static Map getPropertiesMap() {
        Map<String, String> properties = new HashMap<>();

        properties.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/bobocode_db");
        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.put("hibernate.connection.username", "defaust");
        properties.put("hibernate.connection.password", "defaust");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
//        properties.put("hibernate.hbm2ddl.auto", "create");

        return properties;
    }
}
