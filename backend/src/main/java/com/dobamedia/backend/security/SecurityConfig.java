package com.dobamedia.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .httpBasic().and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/", "/static/**", "/index.html", "/api/users/me").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                .requestMatchers("/api/users/login", "/api/users/logout", "/api/users/{username}").hasRole("USER")
                .anyRequest().denyAll()
                .and().build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return passwordEncoder;
    }

    @Bean
    public UserDetailsManager userDetailsService() {

        String udmException = "You cannot use this custom UserDetailsManager for this action.";
        return new UserDetailsManager() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userService.getUserDetails(username);
                if (user == null) {
                    throw new UsernameNotFoundException("Username not found");
                }
                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.username())
                        .password(user.passwordBcrypt())
                        .roles(user.getRole())
                        .build();
            }

            @Override
            public void createUser(UserDetails user) {
                throw new UnsupportedOperationException(udmException);
            }

            @Override
            public void updateUser(UserDetails user) {
                throw new UnsupportedOperationException(udmException);
            }

            @Override
            public void deleteUser(String username) {
                throw new UnsupportedOperationException(udmException);
            }

            @Override
            public void changePassword(String oldPassword, String newPassword) {
                throw new UnsupportedOperationException(udmException);
            }

            @Override
            public boolean userExists(String username) {
                throw new UnsupportedOperationException(udmException);
            }
        };
    }
}
