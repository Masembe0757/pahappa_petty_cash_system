package org.pahappa.pettycashapp.systems.petty_cash_app.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role_table")
public class Role {
    public Role(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int permission;

    @OneToMany(mappedBy="roleAttached", cascade = CascadeType.ALL)
    List<User> users;

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

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
