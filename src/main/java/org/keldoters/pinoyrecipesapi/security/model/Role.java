package org.keldoters.pinoyrecipesapi.security.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<Account> accounts;


    public Role (String name) {
        this.name = name;
    }

//    public void addAccount(Account account) {
//        if (accounts == null) {
//            accounts = new HashSet<>();
//        }
//        accounts.add(account);
//    }
}
