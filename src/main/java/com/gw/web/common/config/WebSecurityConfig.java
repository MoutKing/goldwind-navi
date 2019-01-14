package com.gw.web.common.config;

import com.gw.web.security.CustomAccessDeineHandler;
import com.gw.web.security.CustomAuthenticationEntryPoint;
import com.gw.web.security.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @program: template
 * @description: WebSceurity配置
 * @author: MengGuanghui
 * @create: 2018-12-28 13:41
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserService customUserService;
    // @Autowired
    // SessionRegistry sessionRegistry;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.addFilterBefore(new DemoAuthFilter(authenticationManager()), BasicAuthenticationFilter.class); // 可删除

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/signin").permitAll()
                .antMatchers("/api/auth/logout").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/permission/**").permitAll()
                // .antMatchers("/images/**").permitAll()
                // .antMatchers("/js/**").permitAll()
                // .antMatchers("/css/**").permitAll()
                // .antMatchers("/fonts/**").permitAll()
                // .antMatchers("/favicon.ico").permitAll()
                // .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                // .sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry)
                // .and()
                // .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
                .httpBasic();
        // http.csrf().ignoringAntMatchers("/login"); // 登入API不启用CSFR检查
        // 绑定 CSRF TOKEN 到响应的 HEADER 上
        // http.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);

        // 添加自定义异常入口，处理accessdeine异常
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeineHandler());

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService).passwordEncoder(new BCryptPasswordEncoder());
    }

    // @Bean
    // public SessionRegistry getSessionRegistry(){
    //     SessionRegistry sessionRegistry=new SessionRegistryImpl();
    //     return sessionRegistry;
    // }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



}