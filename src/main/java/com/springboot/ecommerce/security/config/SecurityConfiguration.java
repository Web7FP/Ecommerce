package com.springboot.ecommerce.security.config;


import com.springboot.ecommerce.security.loginError.CustomAuthenticationFailureHandler;
import com.springboot.ecommerce.model.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
        throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                    .requestMatchers("/home/**", "/registration/**", "/product/**", "/css/**", "/js/**", "/fonts/**", "/images/**", "/img/**").permitAll()
                    .requestMatchers("/category-management/**", "/tag-management/**", "/order-management/**", "/admin").hasRole(UserRole.ADMIN.name())
                    .requestMatchers("/product-management/**").hasAnyRole(UserRole.VENDOR.name(),UserRole.ADMIN.name())
                    .requestMatchers("/cart/**", "/setCartSession", "/account/**", "/order/**")
                        .hasAnyRole(UserRole.USER.name(), UserRole.ADMIN.name(), UserRole.VENDOR.name())
                .anyRequest().authenticated()


                .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/setCartSession", true)
                    .failureHandler(customAuthenticationFailureHandler)
                    .usernameParameter("email")
                    .passwordParameter("password")

                .and()
                .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7))
                    .key("367639792442264528482B4D6251655468576D5A7134743777217A25432A462D")
                    .rememberMeParameter("remember-me")

                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)
                    .deleteCookies("SESSION", "remember-me")
                    .logoutSuccessUrl("/login");

        return httpSecurity.build();

    }
}
