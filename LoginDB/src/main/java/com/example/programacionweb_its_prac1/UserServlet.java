package com.example.programacionweb_its_prac1;

import com.google.gson.Gson;
import dao.UserDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import static com.example.programacionweb_its_prac1.AutenticacionServlet.generalKey;

@WebServlet("/user-servlet/*")
public class UserServlet extends HttpServlet {
    private final JsonResponse jResp = new JsonResponse();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String authTokenHeader = req.getHeader("Authorization");

        if (authTokenHeader != null && authTokenHeader.startsWith("Bearer ")) {
            String token = authTokenHeader.split(" ")[1];
            User user = getUserFromToken(token);
            if (user != null) {
                String pathInfo = req.getPathInfo();
                if (pathInfo == null || pathInfo.equals("/")) {
                    // Devolver todos los usuarios
                    UserDAO userDAO = new UserDAO();
                    ArrayList<User> allUsers = userDAO.consultarTodos();
                    ArrayList<User> usersWithoutPassword = new ArrayList<>();
                    for (User u : allUsers) {
                        usersWithoutPassword.add(new User(u.getName(), u.getEmail(), null));
                    }
                    jResp.success(req, resp, "Usuarios encontrados", usersWithoutPassword);
                } else {
                    // Devolver datos de un usuario específico
                    String userId = pathInfo.substring(1);
                    UserDAO userDAO = new UserDAO();
                    User requestedUser = userDAO.consultarPorId(Integer.parseInt(userId));
                    if (requestedUser != null) {
                        requestedUser.setPassword(null);
                        jResp.success(req, resp, "Usuario encontrado", requestedUser);
                    } else {
                        jResp.failed(req, resp, "Usuario no encontrado", 404);
                    }
                }
            } else {
                jResp.failed(req, resp, "Usuario no autenticado", 401);
            }
        } else {
            jResp.failed(req, resp, "Token de autorización no válido", 422);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String authTokenHeader = req.getHeader("Authorization");

        if (authTokenHeader != null && authTokenHeader.startsWith("Bearer ")) {
            String token = authTokenHeader.split(" ")[1];
            User user = getUserFromToken(token);
            if (user != null) {
                BufferedReader reader = req.getReader();
                Gson gson = new Gson();
                User newUser = gson.fromJson(reader, User.class);
                if (newUser != null && newUser.getName() != null && newUser.getEmail() != null && newUser.getPassword() != null) {
                    UserDAO userDAO = new UserDAO();
                    if (userDAO.agregar(newUser)) {
                        jResp.success(req, resp, "Usuario creado exitosamente", newUser);
                    } else {
                        jResp.failed(req, resp, "Error al crear el usuario en la base de datos", 500);
                    }
                } else {
                    jResp.failed(req, resp, "Datos del usuario incompletos", 400);
                }
            } else {
                jResp.failed(req, resp, "Usuario no autenticado", 401);
            }
        }
    }

    // Método para validar el token de autenticación y obtener el usuario
    private User getUserFromToken(String token) {
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(generalKey())
                .build();
        try {
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            int userId = claims.get("userId", Integer.class); // Assuming userId is the key in the JWT claims
            // Consultar la base de datos para obtener el usuario por su ID
            UserDAO userDAO = new UserDAO();
            return userDAO.consultarPorId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
