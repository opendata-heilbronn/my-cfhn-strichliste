package de.codeforheilbronn.mycfhn.strichliste.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://my.cfhn.it", "http://local.my.cfhn.it:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "PATCH")
                .allowCredentials(true);
    }
}
