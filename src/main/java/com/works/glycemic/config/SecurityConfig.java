package com.works.glycemic.config;

import com.works.glycemic.services.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final UserService service;
    public SecurityConfig(UserService service) {
        this.service = service;
    }

    // sql -> jpa query -> user control
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((service)).passwordEncoder(service.encoder());
    }

    // hangi yöntemle giriş yapılarak, rollere göre hangi servis kullanılcak?
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/foods/save","/foods/userFoodList","/foods/foodDelete","/foods/foodUpdate", "/register/login").hasAnyRole("user", "admin")
                .antMatchers("/foods/adminWaitFoodList").hasRole("admin")
                .antMatchers("/foods/list", "foods/detail/**").hasAnyRole("global","user", "admin")
                .antMatchers("/register/userRegister", "/register/adminRegister").permitAll()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .logout().logoutUrl("/logout").invalidateHttpSession(true);
    }
}