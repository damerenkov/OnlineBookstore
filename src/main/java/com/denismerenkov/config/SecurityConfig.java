package com.denismerenkov.config;


import com.denismerenkov.security.jwt.JwtConfigurer;
import com.denismerenkov.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;


    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-ui/**").permitAll()
                .antMatchers("/auth").permitAll()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers("/book/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/book/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/order/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/role").hasRole("ADMIN")
                .antMatchers("/role/admin").permitAll()
                .anyRequest().hasRole("ADMIN")
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
