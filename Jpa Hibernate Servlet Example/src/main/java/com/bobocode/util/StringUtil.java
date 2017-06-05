package com.bobocode.util;


import javax.servlet.http.HttpServletRequest;

public class StringUtil {
    public static String getUrlMapping(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return requestURI.replace(req.getServletPath(), "").replace("/", "");
    }

    public static boolean isId(String mappingUrl) {
        try {
            Long.parseLong(mappingUrl);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
