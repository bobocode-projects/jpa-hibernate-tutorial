package com.bobocode.listener;

import com.bobocode.util.DBUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DBUtil.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBUtil.destroy();
    }
}
