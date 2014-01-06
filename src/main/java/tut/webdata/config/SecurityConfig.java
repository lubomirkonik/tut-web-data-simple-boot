package tut.webdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("l").password("k").roles("USER");  //letsnosh, noshing
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    //{!begin configure}
    http.authorizeUrls()
        //.anyRequest().hasAuthority("ROLE_USER").and().anonymous()
        //.anyRequest().permitAll()
    	.antMatchers("/").permitAll()
    	.antMatchers(HttpMethod.GET, "/showBasket").permitAll()
    	.antMatchers("/addToBasket").permitAll()
    	.antMatchers("/removeFromBasket").permitAll()
    	.antMatchers("/updateMenuItem").permitAll()
    	.antMatchers("/deleteMenuItem").permitAll()
        .antMatchers("/order/**").hasRole("USER")
        .antMatchers("/checkout").hasRole("USER")
        .anyRequest().anonymous()
        .and()
        //This will generate a login form if none is supplied.
        .formLogin();
    //{!end configure}
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
       return super.authenticationManagerBean();
  }
}
