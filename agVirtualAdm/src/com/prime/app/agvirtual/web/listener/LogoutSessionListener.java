package com.prime.app.agvirtual.web.listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogoutSessionListener implements HttpSessionListener {

    private Logger logger = LoggerFactory.getLogger(LogoutSessionListener.class);
    private Map<String, Long> sessions = Collections.synchronizedMap(new HashMap<String, Long>());

    public void sessionCreated(HttpSessionEvent event) {
        sessions.put(event.getSession().getId(), System.currentTimeMillis());
        logger.info("Session " + event.getSession().getId() + " created. Total: " + sessions.size());
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        Long startTime = sessions.get(event.getSession().getId());
        sessions.remove(event.getSession().getId());
        logger.info("Session " + event.getSession().getId() + " destroyed, time up: "
            + (System.currentTimeMillis() - startTime) + "ms. Total: " + sessions.size());
    }

}
