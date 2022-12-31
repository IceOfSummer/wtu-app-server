package pers.xds.wtuapp.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import pers.xds.wtuapp.web.security.handler.AuthFailHandler;
import pers.xds.wtuapp.web.security.handler.LoginSuccessHandler;
import pers.xds.wtuapp.web.security.point.AuthenticationEntryPointImpl;


/**
 * @author DeSen Xu
 * @date 2022-08-30 23:56
 */
@Configuration
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    private LoginSuccessHandler loginSuccessHandler;

    private SecurityContextRepositoryImpl securityContextRepositoryImpl;

    @Autowired
    public void setSecurityContextRepositoryImpl(SecurityContextRepositoryImpl securityContextRepositoryImpl) {
        this.securityContextRepositoryImpl = securityContextRepositoryImpl;
    }

    @Autowired
    public void setLoginSuccessHandler(LoginSuccessHandler loginSuccessHandler) {
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureHandler(new AuthFailHandler())
                .successHandler(loginSuccessHandler);

        http.exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPointImpl());

        http.csrf().disable();

        http.securityContext().securityContextRepository(securityContextRepositoryImpl);

        http.authenticationManager(new AuthenticationManagerBuilder(new ObjectPostProcessor<>() {
            @Override
            public <O> O postProcess(O object) {
                return object;
            }
        }).userDetailsService(userDetailsService).passwordEncoder(PwdEncoder.BCrypt.passwordEncoder).and().build());
        return http.build();
    }

}
