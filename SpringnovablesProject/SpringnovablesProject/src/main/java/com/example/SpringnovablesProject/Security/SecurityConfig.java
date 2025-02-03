package com.example.SpringnovablesProject.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/springnovables/mediciones").authenticated() // Solo autenticados
                        .requestMatchers("/springnovables/mediciones/crear", "/springnovables/mediciones/editar/**", "/springnovables/mediciones/eliminar/**").hasRole("ADMIN") // Solo admin puede crear, editar o eliminar
                        .anyRequest().permitAll() // Resto de páginas accesibles para todos
                )
                .formLogin(login -> login
                        .defaultSuccessUrl("/springnovables/mediciones", true) // Redirige a mediciones tras login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Aquí es donde se define la URL de logout
                        .logoutSuccessUrl("/login") // Redirige a login tras logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("1234")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}

