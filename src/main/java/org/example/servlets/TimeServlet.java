package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine engine;

    @Override
    public void init() throws ServletException {
        engine = new TemplateEngine();
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix(getServletContext().getRealPath("WEB-INF/templates/"));
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rowLocalDateTime;

        StringBuilder stringBuilder = new StringBuilder();
        if (Utils.hasParameter(req, Utils.TIME_ZONE_PATH)) {
            resp.addCookie(new Cookie("lastTimezone", req.getParameter(Utils.TIME_ZONE_PATH)));
            String zoneId = req.getParameter(Utils.TIME_ZONE_PATH);
            rowLocalDateTime = LocalDateTime.now(ZoneId.of(zoneId))
                    .format(DateTimeFormatter
                            .ofPattern("yyyy-MM-dd HH:mm:ss"));
            stringBuilder.append(rowLocalDateTime);
            stringBuilder.append(" ").append(zoneId);

        } else {

            Cookie[] cookies = req.getCookies();
            String lastTimeZone = "UTC";
            if(cookies!= null) {
                for(int i = 0; i<cookies.length; i++) {
                    if(cookies[i].getName().equals("lastTimezone")) {
                        lastTimeZone = cookies[i].getValue();
                    }
                }
            }
            if(!lastTimeZone.equals("UTC")) {
                rowLocalDateTime = LocalDateTime.now(ZoneId.of(lastTimeZone))
                        .format(DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss"));
                stringBuilder.append(rowLocalDateTime);
            } else {
                rowLocalDateTime = LocalDateTime.now()
                        .format(DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss"));
                stringBuilder.append(rowLocalDateTime);
            }
            stringBuilder.append(" " + lastTimeZone);
        }
        resp.setContentType("text/html");
        Context simpleContext = new Context(req.getLocale(), Map.of("time",stringBuilder.toString()));
        engine.process("current_time", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
