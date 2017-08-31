package com.bobocode.listener;

import com.bobocode.util.JpaUtil;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        JpaUtil.init("bobocode");
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        sce.getServletContext().setAttribute("emf", emf);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JpaUtil.close();
    }
}
