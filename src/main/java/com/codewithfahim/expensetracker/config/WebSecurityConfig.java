package com.codewithfahim.expensetracker.config;

import com.codewithfahim.expensetracker.security.CustomUserDetailsService;
import com.codewithfahim.expensetracker.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class WebSecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public JwtRequestFilter authenticationJwtTokenFilter() {
        return new JwtRequestFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/register").permitAll()
                        .anyRequest().authenticated());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.httpBasic();
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public InMemoryUserDetailsManager config() {
//        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//        UserDetails user1 = User.withUsername("fahim").password("fahim012").authorities("admin").build();
//        UserDetails user2 = User.withUsername("Sunny").password("sunny012").authorities("user").build();
//        inMemoryUserDetailsManager.createUser(user1);
//        inMemoryUserDetailsManager.createUser(user2);
//        //auth.userDetailsService(customUserDetailsService);
//        return new InMemoryUserDetailsManager((UserDetails) customUserDetailsService);
//    }
//    @Bean
//    public AuthenticationManager authenticationManagerBean() {
//        return new AuthenticationManager() {
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                return null;
//            }
//        };
//    }

}
