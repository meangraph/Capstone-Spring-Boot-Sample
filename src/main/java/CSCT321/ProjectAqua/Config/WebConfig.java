package CSCT321.ProjectAqua.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// A configuration class that customizes the default Spring MVC settings
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Overridden method to specify Cross-Origin Resource Sharing (CORS) configurations
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply CORS configuration to all endpoints in the application
                .allowedOrigins("http://localhost:3000", "http://localhost:8000") // Specify the origins that are allowed to make CORS requests
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Specify the HTTP methods that are allowed
                .allowCredentials(false) // Specify that credentials are not allowed in CORS requests (This will be changed when adding authentication/security)
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type"); // Specify the headers that are allowed in CORS requests
    }
}

