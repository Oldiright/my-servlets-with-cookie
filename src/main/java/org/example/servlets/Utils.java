package org.example.servlets;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.StringJoiner;

public class Utils {
    public static final String TIME_ZONE_PATH = "timezone";
    public static final String DATE_TIME_FORMAT_TEMPLATE = "yyyy-MM-dd HH:mm:ss";

    public static boolean hasParameter(HttpServletRequest req, String parameterName) {
        Map<String, String[]> parameterMap = req.getParameterMap();
        if(parameterMap.containsKey(parameterName)) {
            return true;
        }
        return false;
    }

    public static String getLocalDateTime(String zoneId) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (zoneId.equals("UTC")) {
            return stringJoiner.add(LocalDateTime.now()
                    .format(DateTimeFormatter
                            .ofPattern(DATE_TIME_FORMAT_TEMPLATE))).add(zoneId).toString();
        } else {
            return stringJoiner.add(LocalDateTime.now(ZoneId.of(zoneId))
                    .format(DateTimeFormatter
                            .ofPattern(DATE_TIME_FORMAT_TEMPLATE))).add(zoneId).toString();
        }
    }
}
