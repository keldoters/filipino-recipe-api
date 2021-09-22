package org.keldoters.pinoyrecipesapi;


import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.common.collect.Lists;
import org.keldoters.pinoyrecipesapi.dto.RecipeDTO;
import org.keldoters.pinoyrecipesapi.model.Recipe;
import org.keldoters.pinoyrecipesapi.repository.IngredientRepository;
import org.keldoters.pinoyrecipesapi.repository.RecipeRepository;
import org.keldoters.pinoyrecipesapi.service.RecipeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootApplication
public class PinoyRecipesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PinoyRecipesApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner runApplication(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, RecipeService recipeService) {
		return  (args -> {
//			RecipeDTO recipeDTO = new RecipeDTO.Builder()
//					.setName("Biko with Latik")
//					.setCategory("Dessert")
//					.setYoutube_url("https://www.youtube.com/watch?v=ThY66jHY-uI")
//					.setCooktime("50 minutes")
//					.setInstruction("Prepare latik by boiling 2 cups coconut milk in a saucepan. Adjust heat to medium. Continue boiling while stirring until texture thickens. Lower the heat once oil becomes visible. Cook and stir constantly until the color of the residue turns golden brown (note: latik is the residue formed after boiling coconut milk). Separate the oil from the latik using a strainer or filter paper. Set aside.\n" +
//							"Start making the biko by combining glutinous rice and water in a rice cooker. Set the cooker to “cook” mode. Wait until the process is complete.\n" +
//							"Boil 4 cups coconut milk in a cooking pot for 7 minutes.\n" +
//							"Add brown sugar and salt. Stir.\n" +
//							"Add cooked rice. Stir until well blended. Continue cooking while stirring until rice absorbs the coconut milk mixture completely.\n" +
//							"Line banana leaves over a baking tray. Brush coconut oil over the leaves.\n" +
//							"Transfer biko into the tray once the consistency becomes thick and sticky (paste-like). Top with latik. Let it cool down for at least 15 minutes.\n" +
//							"Serve. Share and enjoy!")
//					.setIngredients(List.of("glutinous rice", "water", "brown sugar", "coconut milk", "salt"))
//					.setMeasurements(List.of("2 cups (washed)", "1 1/2 cups", "2 cups", "6 cups", "1/2 teaspoon"))
//					.build();
//			recipeService.saveRecipe(recipeDTO);
//			List<RecipeDTO> recipeDTOList = recipeService.findByIngredient("chicken");
//			System.out.println(recipeDTOList);
			String projectId = "pinoy-recipe-f7ab3";
			String objectName = "Pininyahang-Manok.jpg";
			String bucketName = "pinoy-recipe-f7ab3.appspot.com";
			String filePath = "C:/Users/mkeld/Pictures/recipe/Pininyahang-Manok.jpg";

			GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("C:/Users/mkeld/Pictures/recipe/pinoy-recipe.json"))
					.createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

//			Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

			Storage storage = StorageOptions.newBuilder().setProjectId(projectId).setCredentials(credentials).build().getService();
			Bucket bucket =
					storage.get(bucketName, Storage.BucketGetOption.fields(Storage.BucketField.values()));

			// Print bucket metadata
			System.out.println("BucketName: " + bucket.getName());
			BlobId blobId = BlobId.of(bucketName, objectName);
//			storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
//			Blob blob = storage.get(blobId);
//			blob.downloadTo(Paths.get("C:/Users/mkeld/Pictures"));

			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
			storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

			System.out.println(
					"File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);




		});
	}

}
