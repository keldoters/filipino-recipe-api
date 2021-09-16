package org.keldoters.pinoyrecipesapi;

import org.keldoters.pinoyrecipesapi.dto.RecipeDTO;
import org.keldoters.pinoyrecipesapi.model.Recipe;
import org.keldoters.pinoyrecipesapi.repository.IngredientRepository;
import org.keldoters.pinoyrecipesapi.repository.RecipeRepository;
import org.keldoters.pinoyrecipesapi.service.RecipeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class PinoyRecipesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PinoyRecipesApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner runApplication(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, RecipeService recipeService) {
		return  (args -> {
//			RecipeDTO recipeDTO = new RecipeDTO();
//			recipeDTO.setName("Chicken Tinola");
//			recipeDTO.setCategory("Chicken");
//			recipeDTO.setInstruction(List.of("boil chicken", "add knor cubes", "add vegies", "simmer"));
//			recipeDTO.setIngredients(List.of("chicken","knor cube", "vegies", "water"));
//			recipeDTO.setMeasurements(List.of("2 lbs", "1 pc", "1 plate", "2 cups"));
//			recipeService.saveRecipe(recipeDTO);

//			System.out.println(new RecipeDTO(recipeRepository.findByIdEagerly(48L).get()));

		});
	}

}
