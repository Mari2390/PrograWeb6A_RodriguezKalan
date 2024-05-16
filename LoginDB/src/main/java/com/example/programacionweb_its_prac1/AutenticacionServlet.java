package com.example.programacionweb_its_prac1;

import java.io.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import dao.UserDAO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.SecretKey;

@WebServlet("/autenticacion-servlet/*")
public class AutenticacionServlet extends HttpServlet {
    private static final String SECRET_KEY = "mWQKjKflpJSqyj0nDdSG9ZHE6x4tNaXGb35J6d7G5mo=";

    private final JsonResponse jResp = new JsonResponse();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        jResp.failed(req, resp, "404 - Recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        if (req.getPathInfo() == null) {
            jResp.failed(req, resp, "404 - Recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String[] path = req.getPathInfo().split("/");

        if (req.getPathInfo().equals("/")) {
            jResp.failed(req, resp, "404 - Recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
        }

        String action = path[1];

        switch (action) {
            case "register":
                register(req, resp);
                break;
            case "login":
                login(req, resp);
                break;
            case "logout":
                logout(req, resp);
                break;
            default:
                jResp.failed(req, resp, "404 - Recurso no encontrado", HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("Name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (name == null || email == null || password == null) {
            jResp.failed(req, resp, "Todos los campos son obligatorios", HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        UserDAO userDAO = new UserDAO();
        String encryptedPassword = encryptPassword(password);
        User newUser = new User(name, email, encryptedPassword);
        if (userDAO.agregar(newUser)) {
            jResp.success(req, resp, "Usuario creado con éxito", newUser);
        } else {
            jResp.failed(req, resp, "Error al crear el usuario en la base de datos", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String usernameOrEmail = req.getParameter("usernameOrEmail");
        String password = req.getParameter("password");

        // Aquí deberías obtener el usuario de la base de datos utilizando el username o email
        UserDAO userDAO = new UserDAO();
        User user = userDAO.findByUsernameOrEmail(usernameOrEmail);

        if (user != null && verifyPassword(password, user.getPassword())) {
            // Usuario autenticado correctamente
            // Generar token JWT
            Date expiration = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)); // 5 minutes
            String token = Jwts.builder()
                    .setSubject(usernameOrEmail)
                    .setExpiration(expiration)
                    .signWith(generalKey())
                    .compact();
            jResp.success(req, resp, "Usuario autenticado", token);
        } else {
            jResp.failed(req, resp, "Nombre de usuario o contraseña inválidos", HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // No necesitas hacer nada especial para cerrar la sesión en este caso
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("Logged out successfully");
    }

    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean verifyPassword(String inputPassword, String storedPassword) {
        return BCrypt.checkpw(inputPassword, storedPassword);
    }

    public static SecretKey generalKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
