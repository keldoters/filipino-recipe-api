package org.keldoters.pinoyrecipesapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Columns;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "owner_of_recipe")
public class Owner {

    @Id
    @Column(name = "owner_id")
    private Long id;

    @Column(name = "owner_name")
    private String name;

    @MapsId
    @OneToOne(mappedBy = "owner", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private Recipe recipe;

    public Owner(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", recipe=" + recipe +
                '}';
    }

    @PreRemove
    public void removeOwnerFromRecipe() {
        this.recipe.setOwner(null);
    }
}
