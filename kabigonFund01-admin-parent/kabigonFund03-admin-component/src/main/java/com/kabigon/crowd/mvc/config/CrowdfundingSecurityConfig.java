package com.kabigon.crowd.mvc.config;

import com.kabigon.crowd.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//启动全局方法权限控制功能
public class CrowdfundingSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(userDetailsService)
                 .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
      security
              .authorizeRequests()
              .antMatchers("/admin/to/login/page.html")
              .permitAll()
              .antMatchers("/bootstrap/**")
              .permitAll()
              .antMatchers("/css/**")
              .permitAll()
              .antMatchers("/fronts/**")
              .permitAll()
              .antMatchers("/img/**")
              .permitAll()
              .antMatchers("/js/**")
              .permitAll()
              .antMatchers("/script/**")
              .permitAll()
              .antMatchers("/ztree/**")
                .permitAll()
              .antMatchers("/admin/get/page.html")
              //.hasRole("经理")
              .access("hasRole('经理')or hasAnyAuthority('user:get')")
              .anyRequest()
              .authenticated()
              .and()
              .exceptionHandling()
              .accessDeniedHandler(new AccessDeniedHandler() {
                  @Override
                  public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                      httpServletRequest.setAttribute("exception",new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                      httpServletRequest.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(httpServletRequest,httpServletResponse);

                  }
              })
              .and()
              .csrf()
              .disable()
              .formLogin()
              .loginPage("/admin/to/login/page.html")
              .loginProcessingUrl("/security/do/login.html")
              .permitAll()
              .defaultSuccessUrl("/admin/to/main/page.html")
                .usernameParameter("loginAcct")//账号的请求参数名称
                .passwordParameter("userPswd")//密码的请求参数名称

      .and()
      .logout()
      .logoutUrl("/security/do/logout.html")
      .logoutSuccessUrl("/admin/to/login/page.html")


      ;


    }
}
