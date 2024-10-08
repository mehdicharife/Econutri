package ensias.ma.gl.secondyear.twentyfour.econutri.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ensias.ma.gl.secondyear.twentyfour.econutri.config.security.filter.JwtBearerFilter;


@Configuration
@EnableWebSecurity(debug = true)
public class FilterChainConfiguration {
    
    @Autowired
    private JwtBearerFilter jwtBearerFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
            .addFilterAt(jwtBearerFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf(CsrfConfigurer::disable)
            .authorizeHttpRequests(
                request -> request
                    .requestMatchers(HttpMethod.POST, "/jwts", "/jwts/").permitAll()
                    .requestMatchers(HttpMethod.POST, "/users", "/users/").permitAll()
                    .requestMatchers(HttpMethod.GET, "/*.jpg", "/*.jpeg", "/*.png", "/*.webp").permitAll()
                    .anyRequest().authenticated()
            )   
            .build();
    }
}
