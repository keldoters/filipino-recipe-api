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
