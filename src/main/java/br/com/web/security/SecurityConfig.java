package br.com.web.security;


import br.com.web.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) {
        try {
            http.authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/").permitAll()
                    .antMatchers(HttpMethod.POST, "/alunos/").permitAll()
                    .antMatchers(HttpMethod.POST, "/login/").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and().formLogin().permitAll()
                    .and().
                    httpBasic()
                    .and().csrf().disable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user")
                .password("123").roles()
                .and()
                .withUser("admin").password("123")
                .roles("USER", "ADMIN");
    } /*/

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/js/**", "/css/**");
    }
}
