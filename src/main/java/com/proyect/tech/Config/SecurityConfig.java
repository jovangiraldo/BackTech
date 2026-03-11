package com.proyect.tech.Config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    // Inyectamos el filtro para poder usarlo en la cadena
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth                   
                        .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/clients").permitAll()
                        .requestMatchers(HttpMethod.GET,  "/api/pets/by-identifier/**").permitAll()
                        .requestMatchers(HttpMethod.POST,   "/api/pets").permitAll()
                        .requestMatchers(HttpMethod.GET,    "/api/pets/**").permitAll()
                        .requestMatchers(HttpMethod.POST,   "/api/pets/**").permitAll()
                        .requestMatchers(HttpMethod.PUT,    "/api/pets/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/pets/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/error").permitAll()  
                    
                        .requestMatchers(HttpMethod.GET,    "/api/clients/**").permitAll()
                        .requestMatchers(HttpMethod.POST,   "/api/clients/**").permitAll()
                        .requestMatchers(HttpMethod.PUT,    "/api/clients/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/clients/**").permitAll()
                    
                      
                    
                        .requestMatchers(HttpMethod.GET,    "/api/users/**").hasAnyRole("ADMINISTRADOR", "ASISTENTE")
                        .requestMatchers(HttpMethod.POST,   "/api/users/**").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT,    "/api/users/**").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMINISTRADOR")
                    
                        .anyRequest().authenticated()
                    )
                    // 👇 AQUÍ SÍ VA, al mismo nivel que .authorizeHttpRequests()
                    .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            System.err.println("🔴 401 - URI: " + request.getRequestURI() + " | " + authException.getMessage());
                            response.sendError(401, authException.getMessage());
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            System.err.println("🔴 403 - URI: " + request.getRequestURI() + " | " + accessDeniedException.getMessage());
                            response.sendError(403, accessDeniedException.getMessage());
                        })
                    )
                    .formLogin(form -> form.disable())
                    .httpBasic(basic -> basic.disable())
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Tu origen de Angular
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a todas las rutas
        return source;
    }
}
