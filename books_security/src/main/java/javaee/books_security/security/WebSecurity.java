package javaee.books_security.security;

import javaee.books_security.db.UserRepo;
import javaee.books_security.db.UserService;
import javaee.books_security.dto.PermissionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@RequiredArgsConstructor
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserRepo userRepository;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/favourites").authenticated()
                .antMatchers("/add_to_favourite", "/add_to_favourite/**").authenticated()
                .antMatchers("/remove_from_favourite", "/remove_from_favourite/**").authenticated()
                .antMatchers("/add_book").hasAuthority(PermissionEntity.Permission.ADMIN.name())
                .anyRequest().permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable().cors();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new UserService(userRepository);
    }
}