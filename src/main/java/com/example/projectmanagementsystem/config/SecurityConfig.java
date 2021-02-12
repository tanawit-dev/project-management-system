package com.example.projectmanagementsystem.config;

import com.example.projectmanagementsystem.enumeration.EmployeeRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/h2-console", "/h2-console/**").permitAll().and().headers().frameOptions()
                .sameOrigin();

        http.csrf().disable().authorizeRequests().antMatchers("/login", "/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/projects/**/log-work").hasAnyAuthority(EmployeeRole.MANAGER.name(), EmployeeRole.STAFF.name())
                .antMatchers(HttpMethod.POST, "/projects/**").hasAuthority(EmployeeRole.MANAGER.name())
                .antMatchers(HttpMethod.GET, "/projects/*/edit", "/projects/**/delete")
                .hasAuthority(EmployeeRole.MANAGER.name()).anyRequest().authenticated().and().formLogin().and().logout()
                .invalidateHttpSession(true).clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

}
