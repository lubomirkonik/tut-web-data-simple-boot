package tut.webdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import tut.webdata.services.UserService;

//@EnableWebSecurity	
@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled=true)  
@ImportResource(value = "classpath:spring-security-context.xml")
public class SecurityConfig { 		//extends WebSecurityConfigurerAdapter

//  @Override
//  protected void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//    auth.inMemoryAuthentication()
//        .withUser("l").password("k").roles("USER");
//  }

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
////	  <security:http auto-config="true" disable-url-rewriting="true" use-expressions="true">
////    http.authorizeUrls()
//	  http.authorizeRequests()
//    	.antMatchers("/").permitAll()
//    	.antMatchers(HttpMethod.GET, "/showBasket").permitAll()
//    	.antMatchers("/addToBasket").permitAll()
//    	.antMatchers("/removeFromBasket").permitAll()
//    	.antMatchers("/updateMenuItem").permitAll()
//    	.antMatchers("/deleteMenuItem").permitAll()
//        .antMatchers("/order/**").hasRole("USER")
//        .antMatchers("/checkout").hasRole("USER")
//        
////        <security:intercept-url pattern="/favicon.ico" access="permitAll" />
//        .antMatchers("/resources/**").permitAll()
//        .antMatchers("/signin").permitAll()
//        .antMatchers("/signup").permitAll()
//        
////        .anyRequest().hasAuthority("ROLE_USER").and().anonymous()
////        .anyRequest().permitAll()
////        .anyRequest().anonymous()
//        .anyRequest().authenticated()
//        .and()
//        //This will generate a login form if none is supplied.
//        .formLogin()
//        
//        .loginPage("/signin") // authentication-failure-url="/signin?error=1"
//    	.and().logout().logoutUrl("/logout")
//    	.and().rememberMe().rememberMeServices(rememberMeServices()).key("remember-me-key");
//  }

//  @Bean
//  @Override
//  public AuthenticationManager authenticationManagerBean() throws Exception {
//       return super.authenticationManagerBean();
//  }
  
//  public AuthenticationProvider authenticationProvider() {
//  }
  
  @Bean
  public UserService userService() {
	  return new UserService();
  }
  
  @Bean
  public TokenBasedRememberMeServices rememberMeServices() {
	  return new TokenBasedRememberMeServices("remember-me-key", userService());
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
	  return new StandardPasswordEncoder();
  }
}
