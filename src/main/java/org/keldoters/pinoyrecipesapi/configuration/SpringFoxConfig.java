package org.keldoters.pinoyrecipesapi.configuration;

import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableSwagger2
@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiDetails())
                .securitySchemes(List.of(apiKey()))
                .securityContexts(List.of(securityContext()));

    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Filipino Recipes API",
                "API for recipes cooked and prepared in the Philippines",
                "1.0",
                "Terms of Service",
                new springfox.documentation.service.Contact("keldoters", "https://github.com/keldoters/pinoy-recipes-api", "N/A"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0.html",
                Collections.emptyList()
        );
    }

    private ApiKey apiKey() {
        return new ApiKey("jwtToken", "Authorization", SecurityScheme.In.HEADER.name());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(securityReference()).build();

    }

    private List<SecurityReference> securityReference() {
        AuthorizationScope[] authorizationScopes = {new AuthorizationScope("Unlimited", "Full API Permission")};
        return List.of(new SecurityReference("jwtToken", authorizationScopes));
    }
}
