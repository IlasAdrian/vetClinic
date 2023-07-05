package com.sda.vetClinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> {

            auth.requestMatchers("/registration").permitAll();
            auth.requestMatchers("/menu").permitAll();
            auth.requestMatchers("/login").permitAll();
            auth.requestMatchers("/redirectPage").permitAll();

            auth.requestMatchers("/css/*").permitAll();
            auth.requestMatchers("/img/*").permitAll();

            auth.requestMatchers("/home").authenticated();

            auth.requestMatchers("/addPet").hasRole("OWNER");
            auth.requestMatchers("/pets").hasRole("OWNER");
            auth.requestMatchers("/addAppointment").hasRole("OWNER");
            auth.requestMatchers("/appointments").hasRole("OWNER");

            auth.requestMatchers("/appointmentList").hasRole("VET");
            auth.requestMatchers("/appointmentList/*").hasRole("VET");
            auth.requestMatchers("/viewAppointmentList").hasRole("VET");


            auth.requestMatchers("/viewPet/*").hasAnyRole("OWNER","VET");
            auth.requestMatchers("/viewAppointment/*").hasAnyRole("OWNER","VET");


        }).httpBasic();
        httpSecurity
                .formLogin()
                .loginPage("/login").defaultSuccessUrl("/home").permitAll()
                .and().logout().permitAll()
                .and().csrf().disable().authorizeHttpRequests()
                .and().cors().disable().authorizeHttpRequests();

        return httpSecurity.build();
    }
}
