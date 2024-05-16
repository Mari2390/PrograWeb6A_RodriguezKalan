package com.example.programacionweb_its_prac1;

public class User {
    private int id;
    private String name, email, password;

    public User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Este metodo devuelve un array con los valores que se utilizaran para crear el modelo
     * en base de datos
     * @return Object[]
     */
    public Object[] getAll() {
        return new Object[]{
                getName(), getEmail(), getPassword()
        };
    }

    public String toString() {
        return "["
                +
                "ID: " + this.id + "\t" +
                "Nombre: " + this.name + "\t" +
                "Email: " + this.email + "\t" +
                "Password: " + this.password + "\n"
                +
                "]";
    }
}
