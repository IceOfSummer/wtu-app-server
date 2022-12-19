package pers.xds.wtuapp.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
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

    private RedisIndexedSessionRepository sessionRepository;

    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    public void setLoginSuccessHandler(LoginSuccessHandler loginSuccessHandler) {
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Autowired
    public void setFindByIndexNameSessionRepository(RedisIndexedSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureHandler(new AuthFailHandler())
                .successHandler(loginSuccessHandler);

        http.sessionManagement()
                .maximumSessions(1)
                .sessionRegistry(new SpringSessionBackedSessionRegistry(sessionRepository));

        http.exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPointImpl());

        http.csrf().disable();
        http.authenticationManager(new AuthenticationManagerBuilder(new ObjectPostProcessor<>() {
            @Override
            public <O> O postProcess(O object) {
                return object;
            }
        }).userDetailsService(userDetailsService).passwordEncoder(PwdEncoder.BCrypt.passwordEncoder).and().build());
        return http.build();
    }

}
