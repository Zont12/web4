package org.example.back1;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
@Entity
@Table(name="users")

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="username", nullable=false, length=50, unique=true)
    private String username;

    @Column(name="password", nullable=false)
    private String password;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="user")
    private List<Point> points;

    // Конструкторы
    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}
