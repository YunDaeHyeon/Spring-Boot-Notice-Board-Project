package com.noticeboard.configuration;

import com.noticeboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                // 권한 설정
                    .antMatchers("/resources/**","/login-page","/register-page","/login-fail").permitAll() // 모두 허용
                    .antMatchers("/board/list").hasRole("USER")
                    .antMatchers("/board/write").hasRole("USER")
                    .antMatchers("/admin/list").hasRole("ADMIN")
                .and()
                .formLogin()
                // 로그인 설정
                    .loginPage("/login-page")
                    .loginProcessingUrl("/login-action")
                    .usernameParameter("userId")
                    .passwordParameter("userPassword")
                    .successHandler(new LoginSuccessHandler())
                    .failureUrl("/login-fail")
                .and()
                // 로그아웃 설정
                .logout()
                    .logoutUrl("/logout")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .logoutSuccessHandler(new LogoutSuccessHandler() {
                        @Override
                        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                            HttpSession session = request.getSession();
//                          session.invalidate();
                            response.sendRedirect("/login-page");
                        }
                    });
    }
}

class LoginSuccessHandler implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("LoginSuccessHandler : "+authentication.getAuthorities().toString());
        if(authentication.getAuthorities().toString().equals("[ROLE_USER]"))
            response.sendRedirect("/board/list");
        if(authentication.getAuthorities().toString().equals("[ROLE_ADMIN]"))
            response.sendRedirect("/admin/list");
    }
}