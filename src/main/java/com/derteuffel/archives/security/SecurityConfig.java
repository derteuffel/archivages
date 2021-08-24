package com.derteuffel.archives.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/archive/registration/**","/downloadFile/**").permitAll()
                .antMatchers("/archive/**","/direction**","/secteur**").access("hasAnyRole('ROLE_ROOT')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/archive/login")
                .loginProcessingUrl("/archive/login/process")
                .defaultSuccessUrl("/archive/home")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/archive/logout"))
                .logoutSuccessUrl("/archive/login?logout")
                .and()
                .exceptionHandling().accessDeniedPage("/archive/access-denied");
    }



    @Autowired
    private DaoAuthenticationProvider authenticationProvider;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/js/**",
                        "/css/**",
                        "/images/**",
                        "/vendor/**",
                        "/downloadFile/**",
                        "/fonts**",
                        "/static/**"
                );
    }
}
