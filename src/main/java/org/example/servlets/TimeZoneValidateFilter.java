package org.example.servlets;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZoneId;


@WebFilter(value = "/time")
public class  TimeZoneValidateFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if(Utils.hasParameter(req, Utils.TIME_ZONE_PATH)) {
            try {
                ZoneId.of(req.getParameter(Utils.TIME_ZONE_PATH));
            } catch (DateTimeException exception) {
                res.setContentType("text/html: charset=utf-8");
                res.getWriter().write("INVALID TIME ZONE");
                res.getWriter().close();
            }
            chain.doFilter(req, res);
        } else {
            chain.doFilter(req, res);
        }
    }
}
