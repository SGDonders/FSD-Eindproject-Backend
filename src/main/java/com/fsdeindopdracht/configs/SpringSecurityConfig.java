package com.fsdeindopdracht.configs;

import com.fsdeindopdracht.filters.JwtRequestFilter;
import com.fsdeindopdracht.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    private final PasswordEncoder passwordEncoder;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
    }



    // Authenticatie met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeRequests()

                //.antMatchers("/**").permitAll() (put all antmatchers on permitAll, you still have to use a JWT.)

                //--------------------------------Endpoint fileupload------------------------------ -------//
                .antMatchers(HttpMethod.POST, "single/upload/**").permitAll()
                .antMatchers(HttpMethod.GET, "/download/{fileName}").permitAll()
                .antMatchers(HttpMethod.POST, "single/uploadDB/**").permitAll()
                .antMatchers(HttpMethod.GET, "/downloadDB/{fileName}").permitAll()

                //--------------------------------Endpoint orders-------------------------------------------//
                .antMatchers(HttpMethod.POST, "/orders").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/orders").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.DELETE, "/orders").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/orders").hasRole("ADMIN")

                //--------------------------------Endpoint users--------------------------------------------//
                .antMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")

                //------------------------------Endpoint accounts-------------------------------------------//
                .antMatchers(HttpMethod.POST, "/accounts").permitAll()
                .antMatchers(HttpMethod.PUT, "/accounts/**").permitAll()
                .antMatchers(HttpMethod.GET,"/accounts").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST,"/accounts/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/accounts/**").hasRole("ADMIN")

                //------------------------------Endpoint products-------------------------------------------//
                .antMatchers(HttpMethod.GET,"/product").permitAll()
                .antMatchers(HttpMethod.GET,"/product/**").permitAll()
                .antMatchers(HttpMethod.POST, "/product").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH,"/product/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/product/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/product/**").hasRole("ADMIN")

                //----------------------------Endpoint authentication---------------------------------------//
                .antMatchers("/authenticated").authenticated()
                .antMatchers("/authenticate").permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
