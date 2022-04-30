package com.pancarte.climb.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    private final String USERS_QUERY = "select email, password, active from user1 where email=?";
    private final String ROLES_QUERY = "select u.email, r.role from user1 u inner join user_role ur on (u.iduser = ur.iduser) inner join role r on (ur.idrole=r.idrole) where u.email=?";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                 auth.jdbcAuthentication()
                .usersByUsernameQuery(USERS_QUERY)
                .authoritiesByUsernameQuery(ROLES_QUERY)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/publication/**").permitAll()
                .antMatchers("/search/**").anonymous()
                .antMatchers("/login").permitAll()
                .antMatchers("/borrowTopo").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/topo").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/img/*").permitAll()
                .antMatchers("/bootstrap/**").anonymous()
                .antMatchers("/css/**").anonymous()
                .antMatchers("/js/**").anonymous()
                .antMatchers("/assets/*").anonymous()
                .antMatchers("/insertPublication").hasAuthority("ADMIN")
                .antMatchers("/updateVoie").hasAuthority("ADMIN")
                .antMatchers("/unrent").hasAuthority("ADMIN")
                .antMatchers("/validateDele").hasAuthority("ADMIN")
                .antMatchers("/deleCom").hasAuthority("ADMIN")
                .antMatchers("/rent").hasAuthority("ADMIN")
                .antMatchers("/borrow").hasAuthority("ADMIN")
                .antMatchers("/borrowed").hasAuthority("ADMIN")
                .antMatchers("/loggedhome").hasAuthority("ADMIN")
                .antMatchers("/test").hasAuthority("ADMIN")
                .antMatchers("/loggedhome","/test").hasAuthority("ADMIN").anyRequest()

                .authenticated().and().csrf().disable()

                .formLogin().loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/loggedhome")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60*60)
                .and().exceptionHandling().accessDeniedPage("/access_denied");
    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);

        return db;
    }
}
