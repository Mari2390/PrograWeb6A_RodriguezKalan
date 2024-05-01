package com.example.programacionweb_its_prac1;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import static com.example.programacionweb_its_prac1.AutenticacionServlet.generalKey;

@WebServlet("/user-servlet/*")
public class UserServlet extends HttpServlet {
    private final JsonResponse jResp = new JsonResponse();
    private final Map<String, User> users = AutenticacionServlet.users;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String authTokenHeader = req.getHeader("Authorization");
        validateAuthToken(req, resp, authTokenHeader.split(" ")[1]);
    }

    /**
     * Método que se utiliza para validar el token de autenticación. Si el token es válido, se envía una respuesta exitosa.
     * Si el token no es válido, se envía una respuesta fallida.
     * @param req
     * @param resp
     * @param token Token de autenticación
     * @throws IOException
     */
    private void validateAuthToken (HttpServletRequest req, HttpServletResponse resp, String token) throws IOException {
        JwtParser jwtParser = Jwts.parser()
                .verifyWith( generalKey() )
                .build();
        try {
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            User user = users.get(username);
            if (user != null) {
                // Crear un nuevo objeto User sin la contraseña
                User userWithoutPassword = new User(user.getFullName(), user.getEmail(), user.getUsername(), null);
                jResp.success(req, resp, "Autenticación probada", userWithoutPassword);
            } else {
                jResp.failed(req, resp, "Usuario no encontrado", 404);
            }
        } catch (Exception e) {
            jResp.failed(req, resp, "Unathorized: " + e.getMessage(), 401);
        }
    }
}
