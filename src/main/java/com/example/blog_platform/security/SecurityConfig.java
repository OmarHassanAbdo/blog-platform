package com.example.blog_platform.security;

import com.example.blog_platform.security.JWT.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;
    @Bean
    public JwtAuthFilter authenticationJwtTokenFilter() {
        return new JwtAuthFilter();
    }
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(
                    auth -> auth
                            .requestMatchers("/api/public/**").permitAll()
                            .requestMatchers("/api/admin").hasRole("ADMIN")
                            .anyRequest().authenticated()
            );
    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    http.httpBasic(withDefaults());
    return http.build();
}
@Bean
    public BCryptPasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
@Bean
    public AuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
}
@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
    return configuration.getAuthenticationManager();
}
}
