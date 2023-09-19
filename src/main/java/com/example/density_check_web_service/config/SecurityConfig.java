package com.example.density_check_web_service.config;

import com.example.density_check_web_service.domain.Users.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()
            .authorizeRequests()
            .antMatchers("/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
            .antMatchers("/current-location","/report", "/neighbor", "/setting", "/user-management").hasRole(Role.USER.name())
            .anyRequest().permitAll()
            .and()
            .formLogin()
            .loginPage("/customLogin")
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/customLogin")
            .deleteCookies("JSESSIONID", "remember-me")
            .and()
            .oauth2Login()
            .defaultSuccessUrl("/")
            .userInfoEndpoint()
            .userService(customOAuth2UserService);
    }
}
