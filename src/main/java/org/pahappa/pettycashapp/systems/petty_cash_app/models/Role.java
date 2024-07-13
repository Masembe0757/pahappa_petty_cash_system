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

    @OneToMany(mappedBy="roleAttached", cascade = CascadeType.ALL)
    List<User> users;
    @OneToMany(mappedBy="role", cascade = CascadeType.ALL)
    List<Permission> permissions;

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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
