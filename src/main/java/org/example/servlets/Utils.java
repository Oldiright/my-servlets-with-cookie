package org.example.servlets;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class Utils {
    public static final String TIME_ZONE_PATH = "timezone";

    public static boolean hasParameter(HttpServletRequest req, String parameterName) {
        Map<String, String[]> parameterMap = req.getParameterMap();
        if(parameterMap.containsKey(parameterName)) {
            return true;
        }
        return false;
    }
}
