package org.keldoters.pinoyrecipesapi;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.keldoters.pinoyrecipesapi.repository.IngredientRepository;
import org.keldoters.pinoyrecipesapi.repository.RecipeRepository;
import org.keldoters.pinoyrecipesapi.security.repository.AccountRepository;
import org.keldoters.pinoyrecipesapi.security.service.MyUserDetailsService;
import org.keldoters.pinoyrecipesapi.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PinoyRecipesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PinoyRecipesApiApplication.class, args);
//		Scanner scanner = new Scanner(System.in);
//		while (true) {
//
//			Cloudinary cloudinary = cloudinary();
//			System.out.println("enter image name: ");
//			String input = scanner.nextLine();
//			String path = "C:/Users/mkeld/Pictures/recipe/"+input+".jpg";
//			System.out.println(path);
//			if (input.equals("end")) {
//				break;
//			}
//			try {
//				cloudinary.uploader().upload(new File(path),
//						ObjectUtils.asMap("public_id", input,"transformation",
//								new Transformation().width(600).height(600).crop("crop"))
//				);
//				System.out.println("upload complete!");
//			} catch (IOException e) {
//				System.out.println("error upload: " + e.getMessage());
//
//			}
//		}
//		System.out.println("done");
//
//
	}

	@Bean
	public CommandLineRunner runApplication(RecipeRepository recipeRepository,
											IngredientRepository ingredientRepository,
											RecipeService recipeService,
											@Autowired Cloudinary cloudinary,
											MyUserDetailsService myUserDetailsService,
											AccountRepository accountRepository) {

		return (args -> {
//			RecipeDTO recipeDTO = new RecipeDTO.Builder()
//					.setName("Pork Menudo")
//					.setCategory("Pork")
//					.setYoutubeUrl("https://www.youtube.com/watch?v=mhdQUSXitLU")
//					.setCookTime("1 hour 10 minutes")
//					.setInstruction("Heat oil in a pan. Once the oil gets hot, start to pan fry the potato and carrots. Continue until all sides turns light brown. Set aside.\n" +
//							"Heat the remaining oil (add more if needed). Add the pork liver and then stir fry for 30 seconds. Add ground ginger. Continue to stir-fry for 2 minutes. Set aside.\n" +
//							"Pour 3 tablespoons of cooking oil into a cooking pot. Once the oil turns hot, saute garlic and onion.\n" +
//							"Once the onion becomes soft and translucent, add the pork. Saute until light brown.\n" +
//							"Put the hotdogs into the pot. Cook for 1 minute.\n" +
//							"Pour soy sauce, tomato sauce, and water into the pot.\n" +
//							"Add Knorr Pork Cube. Stir.\n" +
//							"Add raisins and dried bay leaves. Stir.Cover the pot and continue to boil between low to medium heat for 45 to 60 minutes, or until the pork is tender.\n" +
//							"Add the pan fried potato and carrots, liver, along with salt and ground black pepper. Stir and cook for 3 minutes.\n" +
//							"Transfer to a serving plate. Serve.\n" +
//							"Share and enjoy!")
//					.setIngredients(List.of("pork shoulder","knorr pork cube","pork liver","potato","hotdog","tomato sauce","carrot","raisins","dried bay leaves","soy sauce","ginger","yellow onion","garlic","cooking oil","water","black pepper","salt"))
//					.setMeasurements(List.of("2 lbs (sliced into small cubes)","1 piece","4 ounces (cubed)","1 piece (cubed)","3 pieces (sliced crosswise into thin pieces)","1 can (8 oz)","1 piece (cubed)","1/2 cup","2 pieces","2 tablespoons","1 teaspoon","1 piece (chopped)","4 cloves (crushed)","3 tablespoons","2 tp 4 cups","buy preference/taste","by preference/taste"))
//					.setImageUrl("https://res.cloudinary.com/dcikpgyxv/image/upload/v1632436824/Pork%20Menudo.jpg")
//					.build();
//			recipeService.saveRecipe(recipeDTO);
//
		});
	}

	@Bean
	public static Cloudinary cloudinary() {
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				"cloud_name", System.getenv("CLOUDNAME"),
				"api_key", System.getenv("APIKEY"),
				"api_secret", System.getenv("APISECRET")));
		return cloudinary;
	}

	@Bean
	public PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

}
