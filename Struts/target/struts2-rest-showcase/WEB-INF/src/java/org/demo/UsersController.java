package org.demo;

// crear una clase UsersController con los siguientes métodos:
// index(): Devuelve la lista de usuarios en formato JSON.
// create(): Crea un nuevo usuario.
// show(): Muestra un usuario específico en formato JSON.
// update(): Actualiza un usuario existente.
// destroy(): Elimina un usuario existente.

import java.util.ArrayList;
import java.util.List;

public class UsersController {
    private List<User> users = new ArrayList<>();

    public String index() {
        // Lógica para devolver la lista de usuarios en formato JSON

        // Devuelve la lista de usuarios en formato JSON
        User user1 = new User(1, "John", "emil");

        users.add(user1);

        

        return "success";
    }

    public String create() {
        // Lógica para crear un nuevo usuario
        return "success";
    }

    public String show() {
        // Lógica para mostrar un usuario específico en formato JSON
        return "success";
    }

    public String update() {
        // Lógica para actualizar un usuario existente
        return "success";
    }

    public String destroy() {
        // Lógica para eliminar un usuario existente
        return "success";
    }

    // Otros métodos auxiliares si es necesario
}

