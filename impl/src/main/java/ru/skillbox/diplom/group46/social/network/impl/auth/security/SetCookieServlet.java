package ru.skillbox.diplom.group46.social.network.impl.auth.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/setCookie")
public class SetCookieServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Создание cookie
        Cookie jwtCookie = new Cookie("jwt", "your_jwt_value_here");
        // Установка срока действия cookie (например, на 30 минут)
        jwtCookie.setMaxAge(30 * 60);
        // Установка пути, для которого cookie будет доступен
        jwtCookie.setPath("/");
        // Установка SameSite=None для куки
        String sameSiteInfo = "SameSite=None";
        // Добавление SameSite к заголовку Set-Cookie
        response.addHeader("Set-Cookie", jwtCookie.getName() + "=" + jwtCookie.getValue() + "; Max-Age=" + jwtCookie.getMaxAge() + "; Path=" + jwtCookie.getPath() + "; " + sameSiteInfo + "; Secure; HttpOnly");
        // Отправка ответа
        response.getWriter().println("Cookie установлен");
    }
}
