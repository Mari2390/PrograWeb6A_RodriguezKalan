package dao;

import com.example.programacionweb_its_prac1.User;
import conexion.Conexion;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO implements DAOGeneral<Integer, User> {
    private final Conexion c;
    public UserDAO() {
        c = new Conexion<User>();
    }

    @Override
    public boolean agregar(User user) {
        String query = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";

        return c.ejecutarActualizacion(query, new Object[]{
                user.getName(),
                user.getEmail(),
                user.getPassword()
        });
    }


    @Override
    public ArrayList<User> consultar() {
        String query = "SELECT * FROM users";

        ArrayList<ArrayList<String>> res = c.ejecutarConsulta(query, new String[]{});
        ArrayList<User> users = new ArrayList<User>();

        for (ArrayList<String> r: res) {
            users.add(new User(r.get(1), r.get(2), r.get(3)));
        }

        return users;
    }

    public ArrayList<User> consultarTodos() {
        String query = "SELECT * FROM users";
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection conn = c.obtener();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                users.add(new User(name, email, password));
            }
            resultSet.close();
            statement.close();
            c.cerrar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User consultarPorId(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try {
            Connection conn = c.obtener();
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                return new User(name, email, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean actualizar(Integer id, User nuevo) {
        String query = "UPDATE users SET name=?, email=?, password=? WHERE id=?";

        return c.ejecutarActualizacion(query, new Object[]{
                nuevo.getName(),
                nuevo.getEmail(),
                nuevo.getPassword(),
                id
        });
    }

    @Override
    public boolean eliminar(Integer id) {
        return false;
    }

    public User findByUsernameOrEmail(String usernameOrEmail) {
        String query = "SELECT * FROM users WHERE name = ? OR email = ?";
        try {
            Connection conn = c.obtener();
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, usernameOrEmail);
            statement.setString(2, usernameOrEmail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                return new User(id, name, email, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
