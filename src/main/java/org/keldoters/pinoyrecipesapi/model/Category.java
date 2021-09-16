package org.keldoters.pinoyrecipesapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category",
               cascade = {CascadeType.DETACH, CascadeType.MERGE,
                         CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Recipe> recipes;

    public Category(String name) {
        this.name = name;
    }

    public void addRecipe(Recipe recipe) {
        if (recipes == null) {
            recipes = new HashSet<>();
        }
        recipes.add(recipe);
        recipe.setCategory(this);
    }


}
