package com.config;

import com.enumerations.AppUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final DataSource dataSource;

    @Autowired
    public SecurityConfig(DataSource dataSource, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.dataSource = dataSource;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withUser("user")
                .password(bCryptPasswordEncoder.encode("user"))
                .roles(AppUserRole.USER.name())
                .and()
                .withUser("admin")
                .password(bCryptPasswordEncoder.encode("admin"))
                .roles(AppUserRole.ADMIN.name())
                .and()
                .usersByUsernameQuery("select username,password,enabled "
                        + "from users "
                        + "where username LIKE ?")
                .authoritiesByUsernameQuery("select username,authority "
                        + "from authorities "
                        + "where username LIKE ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/home").hasRole(AppUserRole.USER.name())
                .antMatchers("/admin").hasRole(AppUserRole.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/api/get").hasAnyAuthority(AppUserRole.USER.name())
                .antMatchers(HttpMethod.POST,"/api/post").hasAnyAuthority(AppUserRole.ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/api/put").hasAnyAuthority(AppUserRole.ADMIN.name())
                .antMatchers(HttpMethod.DELETE,"/api/delete").hasAnyAuthority(AppUserRole.ADMIN.name())
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user1 = User
                .builder()
                .username("admin")
                .password(bCryptPasswordEncoder.encode("admin"))
                .roles(AppUserRole.ADMIN.name())
                .build();

        UserDetails user2 = User
                .builder()
                .username("user")
                .password(bCryptPasswordEncoder.encode("user"))
                .roles(AppUserRole.USER.name())
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}
