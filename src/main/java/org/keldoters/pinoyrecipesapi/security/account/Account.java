package org.keldoters.pinoyrecipesapi.security.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.keldoters.pinoyrecipesapi.security.role.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                       CascadeType.REFRESH},
            fetch = FetchType.EAGER
    )
    @JoinTable(name = "account_role",
               joinColumns = @JoinColumn(name = "account_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }

}
