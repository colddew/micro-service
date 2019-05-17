package edu.ustc.server.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {

    private final String adminServerContextPath;

    public SecuritySecureConfig(AdminServerProperties adminServer) {
        this.adminServerContextPath = adminServer.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(path("/"));

        http.authorizeRequests()
                .antMatchers(path("/assets/**")).permitAll()
                .antMatchers(path("/login")).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(path("/login")).successHandler(successHandler)
                .and()
                .logout().logoutUrl(path("/logout"))
                .and()
                .httpBasic().and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers(
                        path("/instances"),
                        path("/actuator/**")
                );
    }

    private String path(String path) {
        return adminServerContextPath + path;
    }
}