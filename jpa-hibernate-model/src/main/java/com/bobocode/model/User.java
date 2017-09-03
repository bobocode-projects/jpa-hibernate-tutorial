package com.bobocode.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@Getter @Setter @ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private LocalDate birthday;
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Embedded
    private Credentials credentials;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Role> roles = new HashSet<>();

    public User(String firstName, String lastName, LocalDate birthday, Credentials credentials) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.credentials = credentials;
        this.creationDate = LocalDate.now();
    }

    public void setAddress(Address address) {
        address.setUser(this);
        this.address = address;
    }

    public void addRole(Role role) {
        roles.add(role);
        role.setUser(this);
    }

    public void addRoles(List<Role> roles) {
        this.roles.addAll(roles);
        roles.forEach(role -> role.setUser(this));
    }

    public void removeRole(Role role){
        this.roles.remove(role);
        role.setUser(null); // if you do this first, you will break Role#equals() method
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return this.getCredentials().equals(user.getCredentials());
    }

    @Override
    public int hashCode() {
        return this.getCredentials().hashCode();
    }
}
