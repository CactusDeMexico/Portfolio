package com.pancarte.architecte.configuration;

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
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    private final String USERS_QUERY = "select email, password, active from user_account where email=?";
    private final String ROLES_QUERY = "select u.email, r.role from user_account u inner join user_role ur on (u.id_user = ur.id_user) inner join role r on (ur.id_role=r.id_role) where u.email=?";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(USERS_QUERY)
                .authoritiesByUsernameQuery(ROLES_QUERY)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/createUser").permitAll()
                .antMatchers("/queryUsers").permitAll()
                .antMatchers("/queryUserById").permitAll()
                .antMatchers("/updateUserById").permitAll()
                .antMatchers("/deleteUserById").permitAll()
                .antMatchers("/verifyMeeting").permitAll()
                .antMatchers("/queryBlockedEmail").permitAll()
.antMatchers("/quoteMaking").permitAll()

                .antMatchers("/addProject").permitAll()

                .antMatchers("/queryMeeting").permitAll()
                .antMatchers("/queryProject").permitAll()
.antMatchers("/blockEmail").permitAll()

                .antMatchers("/sendMeeting").permitAll()
                .antMatchers("/queryProjectMaterial").permitAll()

                .antMatchers("/queryUserByEmail").permitAll()

                .antMatchers("/queryAllMeeting").permitAll()

                .antMatchers("/addMaterial").permitAll()

                .antMatchers("/queryAllMaterial").permitAll()

                .antMatchers("/loggedhome").hasAuthority("ADMIN").anyRequest()

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
                .tokenValiditySeconds(60 * 60)
                .and().exceptionHandling().accessDeniedPage("/access_denied");
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);

        return db;
    }
}
