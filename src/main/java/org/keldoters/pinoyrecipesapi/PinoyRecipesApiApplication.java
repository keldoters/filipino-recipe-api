package org.keldoters.pinoyrecipesapi;

import org.keldoters.pinoyrecipesapi.model.Owner;
import org.keldoters.pinoyrecipesapi.model.Recipe;
import org.keldoters.pinoyrecipesapi.repository.OwnerRepository;
import org.keldoters.pinoyrecipesapi.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class PinoyRecipesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PinoyRecipesApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner runApplication(RecipeRepository recipeRepository, OwnerRepository ownerRepository) {
		return  (args -> {
			Owner owner1 = new Owner("michael");
			Recipe recipe1 = new Recipe("adobo", "soy sauce");
			recipe1.setOwner(owner1);
			owner1.setRecipe(recipe1);
			recipeRepository.save(recipe1);


			Owner owner2 = new Owner("berto");
			Recipe recipe2 = new Recipe("tinola", "chicken");
			recipe2.setOwner(owner2);
			owner2.setRecipe(recipe2);

			recipeRepository.save(recipe2);

			Owner owner3 = new Owner("john");
			Recipe recipe3 = new Recipe("sinigang", "pork stew");
			recipe3.setOwner(owner3);
			owner3.setRecipe(recipe3);
			recipeRepository.save(recipe3);


			ownerRepository.delete(owner2);




		});
	}

}
